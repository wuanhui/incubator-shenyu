/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.sync.data.etcd;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shenyu.common.constant.DefaultPathConstants;
import org.apache.shenyu.common.dto.AppAuthData;
import org.apache.shenyu.common.dto.DiscoverySyncData;
import org.apache.shenyu.common.dto.MetaData;
import org.apache.shenyu.common.dto.PluginData;
import org.apache.shenyu.common.dto.ProxySelectorData;
import org.apache.shenyu.common.dto.RuleData;
import org.apache.shenyu.common.dto.SelectorData;
import org.apache.shenyu.common.enums.ConfigGroupEnum;
import org.apache.shenyu.common.utils.GsonUtils;
import org.apache.shenyu.sync.data.api.AuthDataSubscriber;
import org.apache.shenyu.sync.data.api.DiscoveryUpstreamDataSubscriber;
import org.apache.shenyu.sync.data.api.MetaDataSubscriber;
import org.apache.shenyu.sync.data.api.PluginDataSubscriber;
import org.apache.shenyu.sync.data.api.ProxySelectorDataSubscriber;
import org.apache.shenyu.sync.data.api.SyncDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Data synchronize of etcd.
 */
public class EtcdSyncDataService implements SyncDataService {

    /**
     * logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(EtcdSyncDataService.class);

    private static final String PRE_FIX = "/shenyu";

    private final EtcdClient etcdClient;

    private final PluginDataSubscriber pluginDataSubscriber;

    private final List<MetaDataSubscriber> metaDataSubscribers;

    private final List<AuthDataSubscriber> authDataSubscribers;

    private final List<ProxySelectorDataSubscriber> proxySelectorDataSubscribers;

    private final List<DiscoveryUpstreamDataSubscriber> discoveryUpstreamDataSubscribers;

    /**
     * Instantiates a new Zookeeper cache manager.
     *
     * @param etcdClient                       the etcd client
     * @param pluginDataSubscriber             the plugin data subscriber
     * @param metaDataSubscribers              the meta data subscribers
     * @param authDataSubscribers              the auth data subscribers
     * @param proxySelectorDataSubscribers     the proxy selector data subscribers
     * @param discoveryUpstreamDataSubscribers the discovery upstream data subscribers
     */
    public EtcdSyncDataService(final EtcdClient etcdClient,
                               final PluginDataSubscriber pluginDataSubscriber,
                               final List<MetaDataSubscriber> metaDataSubscribers,
                               final List<AuthDataSubscriber> authDataSubscribers,
                               final List<ProxySelectorDataSubscriber> proxySelectorDataSubscribers,
                               final List<DiscoveryUpstreamDataSubscriber> discoveryUpstreamDataSubscribers) {
        this.etcdClient = etcdClient;
        this.pluginDataSubscriber = pluginDataSubscriber;
        this.metaDataSubscribers = metaDataSubscribers;
        this.authDataSubscribers = authDataSubscribers;
        this.proxySelectorDataSubscribers = proxySelectorDataSubscribers;
        this.discoveryUpstreamDataSubscribers = discoveryUpstreamDataSubscribers;
        watcherData();
        watchAppAuth();
        watchMetaData();
    }

    private void watcherData() {
        final String pluginParent = DefaultPathConstants.PLUGIN_PARENT;
        List<String> pluginChildren = etcdClientGetChildren(pluginParent);
        for (String pluginName : pluginChildren) {
            watcherAll(pluginName);
        }
        etcdClient.watchChildChange(pluginParent, (pluginPath, updateValue) -> {
            if (!pluginPath.isEmpty()) {
                final String pluginName = pluginPath.substring(pluginPath.lastIndexOf("/") + 1);
                cachePluginData(updateValue);
                subscribePluginDataChanges(pluginPath, pluginName);
            }
        }, null);
    }

    private void watcherAll(final String pluginName) {
        watcherPlugin(pluginName);
        watcherSelector(pluginName);
        watcherRule(pluginName);
    }

    private void watcherPlugin(final String pluginName) {
        String pluginPath = DefaultPathConstants.buildPluginPath(pluginName);
        cachePluginData(etcdClient.get(pluginPath));
        subscribePluginDataChanges(pluginPath, pluginName);
    }

    private void watcherSelector(final String pluginName) {
        String selectorParentPath = DefaultPathConstants.buildSelectorParentPath(pluginName);
        List<String> childrenList = etcdClientGetChildren(selectorParentPath);
        if (CollectionUtils.isNotEmpty(childrenList)) {
            childrenList.forEach(children -> {
                String realPath = buildRealPath(selectorParentPath, children);
                cacheSelectorData(etcdClient.get(realPath));
                subscribeSelectorDataChanges(realPath);
            });
        }
        subscribeChildChanges(ConfigGroupEnum.SELECTOR, selectorParentPath);
    }

    private void watcherRule(final String pluginName) {
        String ruleParent = DefaultPathConstants.buildRuleParentPath(pluginName);
        List<String> childrenList = etcdClientGetChildren(ruleParent);
        if (CollectionUtils.isNotEmpty(childrenList)) {
            childrenList.forEach(children -> {
                String realPath = buildRealPath(ruleParent, children);
                cacheRuleData(etcdClient.get(realPath));
                subscribeRuleDataChanges(realPath);
            });
        }
        subscribeChildChanges(ConfigGroupEnum.RULE, ruleParent);
    }

    private void watchAppAuth() {
        final String appAuthParent = DefaultPathConstants.APP_AUTH_PARENT;
        List<String> childrenList = etcdClientGetChildren(appAuthParent);
        if (CollectionUtils.isNotEmpty(childrenList)) {
            childrenList.forEach(children -> {
                String realPath = buildRealPath(appAuthParent, children);
                cacheAuthData(etcdClient.get(realPath));
                subscribeAppAuthDataChanges(realPath);
            });
        }
        subscribeChildChanges(ConfigGroupEnum.APP_AUTH, appAuthParent);
    }

    private void watchMetaData() {
        final String metaDataPath = DefaultPathConstants.META_DATA;
        List<String> childrenList = etcdClientGetChildren(metaDataPath);
        if (CollectionUtils.isNotEmpty(childrenList)) {
            childrenList.forEach(children -> {
                String realPath = buildRealPath(metaDataPath, children);
                cacheMetaData(etcdClient.get(realPath));
                subscribeMetaDataChanges(realPath);
            });
        }
        subscribeChildChanges(ConfigGroupEnum.META_DATA, metaDataPath);
    }

    private void subscribeChildChanges(final ConfigGroupEnum groupKey, final String groupParentPath) {
        switch (groupKey) {
            case SELECTOR:
                etcdClient.watchChildChange(groupParentPath, (updatePath, updateValue) -> {
                    cacheSelectorData(updateValue);
                    subscribeSelectorDataChanges(updatePath);
                }, null);
                break;
            case RULE:
                etcdClient.watchChildChange(groupParentPath, (updatePath, updateValue) -> {
                    cacheRuleData(updateValue);
                    subscribeRuleDataChanges(updatePath);
                }, null);
                break;
            case APP_AUTH:
                etcdClient.watchChildChange(groupParentPath, (updatePath, updateValue) -> {
                    cacheAuthData(updateValue);
                    subscribeAppAuthDataChanges(updatePath);
                }, null);
                break;
            case META_DATA:
                etcdClient.watchChildChange(groupParentPath, (updatePath, updateValue) -> {
                    cacheMetaData(updateValue);
                    subscribeMetaDataChanges(updatePath);
                }, null);
                break;
            case DISCOVER_UPSTREAM:
                etcdClient.watchChildChange(groupParentPath, (updatePath, updateValue) -> {
                    cacheDiscoveryUpstreamData(updateValue);
                    subscribeDiscoveryUpstreamDataChanges(updatePath);
                }, null);
                break;
            case PROXY_SELECTOR:
                etcdClient.watchChildChange(groupParentPath, (updatePath, updateValue) -> {
                    cacheProxySelectorData(updateValue);
                    subscribeProxySelectorDataChanges(updatePath);
                }, null);
                break;
            default:
                throw new IllegalStateException("Unexpected groupKey: " + groupKey);
        }
    }

    private void subscribePluginDataChanges(final String pluginPath, final String pluginName) {
        etcdClient.watchDataChange(pluginPath, (updatePath, updateValue) -> {
            final PluginData data = GsonUtils.getInstance().fromJson(updateValue, PluginData.class);
            Optional.ofNullable(data)
                    .ifPresent(d -> Optional.ofNullable(pluginDataSubscriber).ifPresent(e -> e.onSubscribe(d)));
        }, deleteNode -> deletePlugin(pluginName));
    }

    private void deletePlugin(final String pluginName) {
        final PluginData data = new PluginData();
        data.setName(pluginName);
        Optional.ofNullable(pluginDataSubscriber).ifPresent(e -> e.unSubscribe(data));
    }

    private void subscribeSelectorDataChanges(final String path) {
        etcdClient.watchDataChange(path, (updateNode, updateValue) -> cacheSelectorData(updateValue),
                this::unCacheSelectorData);
    }

    private void subscribeRuleDataChanges(final String path) {
        etcdClient.watchDataChange(path, (updatePath, updateValue) -> cacheRuleData(updateValue),
                this::unCacheRuleData);
    }

    private void subscribeAppAuthDataChanges(final String realPath) {
        etcdClient.watchDataChange(realPath, (updatePath, updateValue) -> cacheAuthData(updateValue),
                this::unCacheAuthData);
    }

    private void subscribeMetaDataChanges(final String realPath) {
        etcdClient.watchDataChange(realPath, (updatePath, updateValue) -> cacheMetaData(updateValue),
                this::deleteMetaData);
    }

    private void deleteMetaData(final String deletePath) {
        final String path = deletePath.substring(DefaultPathConstants.META_DATA.length() + 1);
        MetaData metaData = new MetaData();

        try {
            metaData.setPath(URLDecoder.decode(path, StandardCharsets.UTF_8.name()));
            unCacheMetaData(metaData);
            etcdClient.watchClose(path);
        } catch (UnsupportedEncodingException e) {
            LOG.error("delete meta data error.", e);
        }
    }

    private void cachePluginData(final String dataString) {
        final PluginData pluginData = GsonUtils.getInstance().fromJson(dataString, PluginData.class);
        Optional.ofNullable(pluginData)
                .flatMap(data -> Optional.ofNullable(pluginDataSubscriber)).ifPresent(e -> e.onSubscribe(pluginData));
    }

    private void cacheSelectorData(final String dataString) {
        final SelectorData selectorData = GsonUtils.getInstance().fromJson(dataString, SelectorData.class);
        Optional.ofNullable(selectorData)
                .ifPresent(data -> Optional.ofNullable(pluginDataSubscriber).ifPresent(e -> e.onSelectorSubscribe(data)));
    }

    private void unCacheSelectorData(final String dataPath) {
        SelectorData selectorData = new SelectorData();
        final String selectorId = dataPath.substring(dataPath.lastIndexOf("/") + 1);
        final String str = dataPath.substring(DefaultPathConstants.SELECTOR_PARENT.length());
        final String pluginName = str.substring(1, str.length() - selectorId.length() - 1);
        selectorData.setPluginName(pluginName);
        selectorData.setId(selectorId);
        Optional.ofNullable(pluginDataSubscriber).ifPresent(e -> e.unSelectorSubscribe(selectorData));
        etcdClient.watchClose(dataPath);
    }

    private void cacheRuleData(final String dataString) {
        final RuleData ruleData = GsonUtils.getInstance().fromJson(dataString, RuleData.class);
        Optional.ofNullable(ruleData)
                .ifPresent(data -> Optional.ofNullable(pluginDataSubscriber).ifPresent(e -> e.onRuleSubscribe(data)));
    }

    private void unCacheRuleData(final String dataPath) {
        String substring = dataPath.substring(dataPath.lastIndexOf("/") + 1);
        final String str = dataPath.substring(DefaultPathConstants.RULE_PARENT.length());
        final String pluginName = str.substring(1, str.length() - substring.length() - 1);
        final List<String> list = Lists.newArrayList(Splitter.on(DefaultPathConstants.SELECTOR_JOIN_RULE).split(substring));

        RuleData ruleData = new RuleData();
        ruleData.setPluginName(pluginName);
        ruleData.setSelectorId(list.get(0));
        ruleData.setId(list.get(1));

        Optional.ofNullable(pluginDataSubscriber).ifPresent(e -> e.unRuleSubscribe(ruleData));
        etcdClient.watchClose(dataPath);
    }

    private void cacheAuthData(final String dataString) {
        final AppAuthData appAuthData = GsonUtils.getInstance().fromJson(dataString, AppAuthData.class);
        Optional.ofNullable(appAuthData)
                .ifPresent(data -> authDataSubscribers.forEach(e -> e.onSubscribe(data)));
    }

    private void unCacheAuthData(final String dataPath) {
        final String key = dataPath.substring(DefaultPathConstants.APP_AUTH_PARENT.length() + 1);
        AppAuthData appAuthData = new AppAuthData();
        appAuthData.setAppKey(key);
        authDataSubscribers.forEach(e -> e.unSubscribe(appAuthData));
        etcdClient.watchClose(dataPath);
    }

    private void cacheMetaData(final String dataString) {
        final MetaData metaData = GsonUtils.getInstance().fromJson(dataString, MetaData.class);
        Optional.ofNullable(metaData)
                .ifPresent(data -> metaDataSubscribers.forEach(e -> e.onSubscribe(metaData)));
    }

    private void unCacheMetaData(final MetaData metaData) {
        Optional.ofNullable(metaData)
                .ifPresent(data -> metaDataSubscribers.forEach(e -> e.unSubscribe(metaData)));
    }

    private void cacheDiscoveryUpstreamData(final String dataString) {
        final DiscoverySyncData discoveryUpstream = GsonUtils.getInstance().fromJson(dataString, DiscoverySyncData.class);
        Optional.ofNullable(discoveryUpstream)
                .ifPresent(data -> discoveryUpstreamDataSubscribers.forEach(e -> e.onSubscribe(data)));
    }

    private void unCacheDiscoveryUpstreamData(final String dataPath) {
        DiscoverySyncData discoverySyncData = new DiscoverySyncData();
        final String selectorId = dataPath.substring(dataPath.lastIndexOf("/") + 1);
        final String str = dataPath.substring(DefaultPathConstants.DISCOVERY_UPSTREAM.length());
        final String pluginName = str.substring(1, str.length() - selectorId.length() - 1);
        discoverySyncData.setPluginName(pluginName);
        discoverySyncData.setSelectorId(selectorId);
        discoveryUpstreamDataSubscribers.forEach(e -> e.unSubscribe(discoverySyncData));
        etcdClient.watchClose(dataPath);
    }

    private void subscribeDiscoveryUpstreamDataChanges(final String realPath) {
        etcdClient.watchDataChange(realPath, (updatePath, updateValue) -> cacheDiscoveryUpstreamData(updateValue),
                this::unCacheDiscoveryUpstreamData);
    }

    private void cacheProxySelectorData(final String dataString) {
        final ProxySelectorData proxySelectorData = GsonUtils.getInstance().fromJson(dataString, ProxySelectorData.class);
        Optional.ofNullable(proxySelectorData)
                .ifPresent(data -> proxySelectorDataSubscribers.forEach(e -> e.onSubscribe(data)));
    }

    private void unCacheProxySelectorData(final String dataPath) {
        ProxySelectorData proxySelectorData = new ProxySelectorData();
        final String selectorId = dataPath.substring(dataPath.lastIndexOf("/") + 1);
        final String str = dataPath.substring(DefaultPathConstants.PROXY_SELECTOR.length());
        final String pluginName = str.substring(1, str.length() - selectorId.length() - 1);
        proxySelectorData.setPluginName(pluginName);
        proxySelectorData.setId(selectorId);
        proxySelectorDataSubscribers.forEach(e -> e.unSubscribe(proxySelectorData));
        etcdClient.watchClose(dataPath);
    }

    private void subscribeProxySelectorDataChanges(final String realPath) {
        etcdClient.watchDataChange(realPath, (updatePath, updateValue) -> cacheProxySelectorData(updateValue),
                this::unCacheProxySelectorData);
    }

    private String buildRealPath(final String parent, final String children) {
        return String.join("/", parent, children);
    }

    private List<String> etcdClientGetChildren(final String parent) {
        try {
            return etcdClient.getChildrenKeys(parent, "/");
        } catch (ExecutionException | InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void close() {
        if (Objects.nonNull(etcdClient)) {
            etcdClient.close();
        }
    }
}
