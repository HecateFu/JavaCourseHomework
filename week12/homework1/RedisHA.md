# 主从

## 主节点配置

```
#主节点
bind 127.0.0.1

#端口
port 6380

#日志文件
logfile "./log/6380.log"

#数据文件
dbfilename "dump-6380.rdb"
```



## 从节点配置

```
#从节点1

bind 127.0.0.1

#端口
port 6381

#日志文件
logfile "./log/6381.log"

#数据文件
dbfilename "dump-6381.rdb"

#指定主节点(数据同步节点)
slaveof 127.0.0.1 6380
```



# Sentinel

## 主节点配置

```
#主节点
bind 127.0.0.1

#端口
port 6380

#日志文件
logfile "./log/6380.log"

#数据文件
dbfilename "dump-6380.rdb"
```



## 从1配置

```
#从节点1

bind 127.0.0.1

#端口
port 6381

#日志文件
logfile "./log/6381.log"

#数据文件
dbfilename "dump-6381.rdb"

#指定主节点(数据同步节点)
replicaof 127.0.0.1 6380
```



## 从2配置

```
#从节点1

bind 127.0.0.1

#端口
port 6382

#日志文件
logfile "./log/6382.log"

#数据文件
dbfilename "dump-6382.rdb"

#指定主节点(数据同步节点)
replicaof 127.0.0.1 6380
```



## Sentinel1配置

```
port 16380
daemonize yes
logfile "./log/16380.log"
dir "/redis/sentinel_163820"
sentinel deny-scripts-reconfig yes
sentinel monitor mymaster 127.0.0.1 6380 2
sentinel failover-timeout mymaster 15000
sentinel auth-pass mymaster 123
bind 127.0.0.1
```



## Sentinel2配置

```
port 16381
daemonize yes
logfile "./log/16381.log"
dir "/redis/sentinel_16381"
sentinel deny-scripts-reconfig yes
sentinel monitor mymaster 127.0.0.1 6380 2
sentinel failover-timeout mymaster 15000
sentinel auth-pass mymaster 123
bind 127.0.0.1
```



## Sentinel3配置

```
port 16382
daemonize yes
logfile "./log/16382.log"
dir "/redis/sentinel_16382"
sentinel deny-scripts-reconfig yes
sentinel monitor mymaster 127.0.0.1 6380 2
sentinel failover-timeout mymaster 15000
sentinel auth-pass mymaster 123
bind 127.0.0.1
```



# Cluster

## 节点1配置

```
#主节点1
bind 127.0.0.1

#端口
port 6380

#日志文件
logfile "./log/6380.log"

#数据文件
dbfilename "dump-6380.rdb"

cluster-enabled yes
cluster-config-file nodes-6380.conf
cluster-require-full-coverage no
```



## 节点2配置

```
#主节点2

bind 127.0.0.1

#端口
port 6381

#日志文件
logfile "./log/6381.log"

#数据文件
dbfilename "dump-6381.rdb"

cluster-enabled yes
cluster-config-file nodes-6381.conf
cluster-require-full-coverage no
```



## 节点3配置

```
#主节点3

bind 127.0.0.1

#端口
port 6382

#日志文件
logfile "./log/6382.log"

#数据文件
dbfilename "dump-6382.rdb"

cluster-enabled yes
cluster-config-file nodes-6382.conf
cluster-require-full-coverage no
```

## 创建集群

```
redis-cli --cluster create 127.0.0.1:6380 127.0.0.1:6381 127.0.0.1:6382  --cluster-replicas 1
```

