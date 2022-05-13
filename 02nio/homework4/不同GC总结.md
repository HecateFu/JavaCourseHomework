# 不同GC的对比总结

基于jdk1.8.0_312 win10操作系统，根据对GCLogAnalysis在不同GC策略和堆内存配置下执行，得到的gc日志分析结果来看：

1. 除了在265m时，g1gc表现不太稳定，会出现内存溢出的情况，自512m以上的堆内存中，g1gc的表现都是最好的，这和我之前认为的g1gc更适合4g的大堆的说法存在差异；
2. 另一个与我的预期存在差异的是ParallelGC在堆内存较小时（256m和512m）的表现，虽然单次gc数据好于SerialGC，但是GC次数却大于SerialGC，导致总体表现差于SerialGC。此前我一直认为人多好办事，parallelGC应该在各阶段表现都好于SerialGC。
3. ParallelGC的最佳表现实在1g、2g阶段，SerialGC的最佳表现是在1g以下阶段。
4. CMS GC的最佳表现实在1g时，1g以下时它居然是除G1以外的吞吐量最佳，这也违背我此前的认知，这也许和gceasy的吞吐量评价指标（业务线程执行时间：GC活动时间）有关？
5. CMS在没有触发老年代并发GC的情况下，年轻代的 ParNew好像是干不过 Parallel Scavenge 的。
6. 结论：堆内存不是特别小（256m以下）的情况下优先考虑G1 GC，256m以下使用SerialGC；没有G1 GC可以用的时候，256m-1g优先CMS GC，1g-4g优先ParallelGC。
7. 以上结论存在的问题：测试代码GCLogAnalysis运行时间短、为了演示gc效果模拟对象创建、测试环境为我的个人PC，程序运行特点与实际工作中长时间运行的的业务系统存在较大出入，对2g以上的堆内存full gc情况、并发gc情况验证不充分，故以上结论的通用性有限，实际使用中还是应该根据业务系统的实际压测数据选择jvm参数，不能凭印象随便加参数。



------

以下数据来自 gceasy 分析结果

# 256M GC表现

| GC策略   | 吞吐量  | 平均暂停时间 | 最大暂停时间 | 总暂停时间 | 并发GC时间 |
| -------- | ------- | ------------ | ------------ | ---------- | ---------- |
| Serial   | 22.995% | 28.8 ms      | 70.0 ms      | 720 ms     | /          |
| Parallel | 12.514% | 28.2 ms      | 60.0 ms      | 790 ms     | /          |
| CMS      | 26.237% | 22.6 ms      | 90.0 ms      | 790 ms     | 100 ms     |
| G1       | 78.684% | 5.53 ms      | 40.0 ms      | 570 ms     | 72.1 ms    |

注：G1 5次执行中2次出现内存溢出

# 512M GC表现

| GC策略            | 吞吐量  | 平均暂停时间 | 最大暂停时间 | 总暂停时间 | 并发GC时间 |
| ----------------- | ------- | ------------ | ------------ | ---------- | ---------- |
| Serial            | 33.014% | 40.0 ms      | 100 ms       | 560 ms     | /          |
| Parallel（8线程） | 21.143% | 25.6 ms      | 90.0 ms      | 690 ms     | /          |
| Parallel（2线程） | 30.206% | 25.4 ms      | 80.0 ms      | 610 ms     | /          |
| CMS               | 52.334% | 15.5 ms      | 90.0 ms      | 480 ms     | 290 ms     |
| G1                | 60.878% | 5.86 ms      | 70.0 ms      | 410 ms     | 51.3 ms    |

# 1G GC表现

| GC策略            | 吞吐量  | 平均暂停时间 | 最大暂停时间 | 总暂停时间 | 并发GC时间 |
| ----------------- | ------- | ------------ | ------------ | ---------- | ---------- |
| Serial            | 39.573% | 56.7 ms      | 100 ms       | 510 ms     | /          |
| Parallel（8线程） | 46.86%  | 31.4 ms      | 100 ms       | 440 ms     | /          |
| CMS               | 58.034% | 35.0 ms      | 60.0 ms      | 350 ms     | 310 ms     |
| G1                | 68.815% | 8.57 ms      | 20.0 ms      | 300 ms     | 33.6 ms    |

# 2G GC表现

| GC策略   | 吞吐量  | 平均暂停时间 | 最大暂停时间 | 总暂停时间 | 并发GC时间 |
| -------- | ------- | ------------ | ------------ | ---------- | ---------- |
| Serial   | 40.514% | 92.5 ms      | 130 ms       | 370 ms     | /          |
| Parallel | 66.805% | 40.0 ms      | 50.0 ms      | 240 ms     | /          |
| CMS      | 45.792% | 76.0 ms      | 100 ms       | 380 ms     | /          |
| G1       | 76.562% | 15.0 ms      | 30.0 ms      | 210 ms     | 8.62 ms    |

注：CMS未出现老年代并发gc，全靠年轻代ParNew了

# 4G GC表现

| GC策略   | 吞吐量  | 平均暂停时间 | 最大暂停时间 | 总暂停时长 | 并发GC时长 |
| -------- | ------- | ------------ | ------------ | ---------- | ---------- |
| Serial   | 16.435% | 150 ms       | 170 ms       | 300 ms     | /          |
| Parallel | 51.768% | 75.0 ms      | 90.0 ms      | 150 ms     | /          |
| CMS      | 45.148% | 78.0 ms      | 100 ms       | 390 ms     | /          |
| G1       | 63.61%  | 22.7 ms      | 30.0 ms      | 250 ms     | /          |

注：CMS、G1均未触发并发gc

# 测试参数

测试程序，案例代码 GCLogAnalysis

```
java -Xms256m -Xmx256m -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.256.Serial.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.256.Serial.jfr GCLogAnalysis
java -Xms512m -Xmx512m -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.512.Serial.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.512.Serial.jfr GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.1g.Serial.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.1g.Serial.jfr GCLogAnalysis
java -Xms2g -Xmx2g -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.2g.Serial.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.2g.Serial.jfr GCLogAnalysis
java -Xms4g -Xmx4g -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.4g.Serial.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.4g.Serial.jfr GCLogAnalysis

java -Xms256m -Xmx256m -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.256.Parallel.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.256.Parallel.jfr GCLogAnalysis
java -Xms256m -Xmx256m -XX:+UseParallelGC -XX:ParallelGCThreads=2 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.256.Parallel2.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.256.Parallel2.jfr GCLogAnalysis
java -Xms512m -Xmx512m -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.512.Parallel.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.512.Parallel.jfr GCLogAnalysis
java -Xms512m -Xmx512m -XX:+UseParallelGC -XX:ParallelGCThreads=2 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.512.Parallel2.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.512.Parallel2.jfr GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.1g.Parallel.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.1g.Parallel.jfr GCLogAnalysis
java -Xms2g -Xmx2g -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.2g.Parallel.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.2g.Parallel.jfr GCLogAnalysis
java -Xms4g -Xmx4g -XX:+UseParallelGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.4g.Parallel.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.4g.Parallel.jfr GCLogAnalysis

java -Xms256m -Xmx256m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.256.CMS.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.256.CMS.jfr GCLogAnalysis
java -Xms512m -Xmx512m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.512.CMS.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.512.CMS.jfr GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.1g.CMS.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.1g.CMS.jfr GCLogAnalysis
java -Xms2g -Xmx2g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.2g.CMS.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.2g.CMS.jfr GCLogAnalysis
java -Xms4g -Xmx4g -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.4g.CMS.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.4g.CMS.jfr GCLogAnalysis

java -Xms256m -Xmx256m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.256.g1.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.256.g1.jfr GCLogAnalysis
java -Xms512m -Xmx512m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.512.g1.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.512.g1.jfr GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.1g.g1.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.1g.g1.jfr GCLogAnalysis
java -Xms2g -Xmx2g -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.2g.g1.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.2g.g1.jfr GCLogAnalysis
java -Xms4g -Xmx4g -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.4g.g1.log -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=filename=flight.4g.g1.jfr GCLogAnalysis
```
