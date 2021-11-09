# 查询服务（wallet-service）



## 接口说明

当前微服务提供实时天气信息、船票信息、人流量预测等查询服务



## 接口一览

### 1. getWeather

- 基于高德地图API查询中国的省或市或区的实时天气信息						

| URL                                    | Request Method |
| -------------------------------------- | -------------- |
| http://127.0.0.1:8081/query/getWeather | GET            |

#### 请求参数说明

| 请求参数 | 类型   | 必填 | 参数说明           | 示例                           |
| -------- | ------ | ---- | ------------------ | ------------------------------ |
| city     | String | true | 要查询的省或市或区 | 广东省<br />广州市<br />越秀区 |

#### 返回参数说明

- 该接口直接返回调用高德地图API返回的接口信息，以下返回参数取自[高德地图API文档](https://lbs.amap.com/api/webservice/guide/api/weatherinfo/)

| 返回参数            | 参数类型 | 参数说明                                                     |
| ------------------- | -------- | ------------------------------------------------------------ |
| status              | String   | 返回状态。值为0或1<br />1：成功；0：失败                     |
| count               | String   | 返回结果总数目                                               |
| info                | String   | 返回的状态信息                                               |
| infocode            | String   | 返回状态说明,10000代表正确                                   |
| lives               | List     | 实况天气数据信息                                             |
| lives.province      | String   | 省份名                                                       |
| lives.city          | String   | 城市名                                                       |
| lives.adcode        | String   | 区域编码，由[高德地图开发者平台](https://lbs.amap.com/api/webservice/download)提供 |
| lives.weather       | String   | 天气现象（汉字描述）                                         |
| lives.temperature   | String   | 实时气温，单位：摄氏度                                       |
| lives.winddirection | String   | 风向描述                                                     |
| lives.windpower     | String   | 风力级别，单位：级                                           |
| lives.humidity      | String   | 空气湿度                                                     |
| lives.reporttime    | String   | 数据发布的时间                                               |

#### 返回示例

##### 成功

```json
{
    "status": "1",
    "count": "1",
    "info": "OK",
    "infocode": "10000",
    "lives":[
        {
            "province": "广东",
            "city": "越秀区",
            "adcode": "440104",
            "weather": "晴",
            "temperature": "15",
            "winddirection": "北",
            "windpower": "≤3",
            "humidity": "32",
            "reporttime": "2021-11-09 10:00:57"
        }
    ]
}
```

##### 失败

```json
{
    "status": "1",
    "count": "1",
    "info": "OK",
    "infocode": "10000",
    "lives":[
        []
    ]
}
```

<br/>

### 2. getTicketsInformation

- 查询票务信息

| URL                                               | Request Method |
| ------------------------------------------------- | -------------- |
| http://127.0.0.1:8081/query/getTicketsInformation | GET            |

#### 请求参数说明

| 请求参数        | 类型   | 必填 | 参数说明 | 示例 |
| --------------- | ------ | ---- | -------- | ---- |
| departureCity   | String | true | 出发地   | 海口 |
| destinationCity | String | true | 目的地   | 海安 |

#### 返回参数说明

| 返回参数 | 参数类型 | 参数说明                               |
| -------- | -------- | -------------------------------------- |
| data     | List     | 符合输入的出发地和目的地的所有票次信息 |

#### 返回示例

##### 成功

```json
"data":[
    {
        "id": 1,
        "classes": "D1730",
        "departureCity": "海口",
        "destinationCity": "海安",
        "departureTime": "10:00:00",
        "ticketSurplus": 58,
        "money": 45.0
    },
    {
        "id": 2,
        "classes": "D1731",
        "departureCity": "海口",
        "destinationCity": "海安",
        "departureTime": "12:00:00",
        "ticketSurplus": 59,
        "money": 40.0
    },
    {
        "id": 3,
        "classes": "D1732",
        "departureCity": "海口",
        "destinationCity": "海安",
        "departureTime": "14:00:00",
        "ticketSurplus": 60,
        "money": 45.0
    },
    {
        "id": 4,
        "classes": "D1733",
        "departureCity": "海口",
        "destinationCity": "海安",
        "departureTime": "16:00:00",
        "ticketSurplus": 60,
        "money": 45.0
    },
    {
        "id": 5,
        "classes": "D1734",
        "departureCity": "海口",
        "destinationCity": "海安",
        "departureTime": "18:00:00",
        "ticketSurplus": 60,
        "money": 40.0
    }
]
}
```

##### 失败

###### 未找到票务数据

```json
{
    "data":[]
}
```

<br/>

### 3. getTicketSurplus

- 查询票余额

| URL                                          | Request Method |
| -------------------------------------------- | -------------- |
| http://127.0.0.1:8081/query/getTicketSurplus | GET            |

#### 请求参数说明

| 请求参数 | 类型   | 必填 | 参数说明 | 示例 |
| -------- | ------ | ---- | -------- | ---- |
| tickedId | String | true | 票ID     | 1    |

#### 返回参数说明

| 返回参数 | 参数类型 | 参数说明                                           |
| -------- | -------- | -------------------------------------------------- |
| 无参数名 | Integer  | 符合输入的票ID的票余额，若未找到该票ID数据，返回-1 |

#### 返回示例

##### 成功

```json
58
```

##### 失败

###### 未找到该票ID数据

```json
-1
```



### 4. getPrediction

- 使用深度学习模型预测指定时间点的人流量

| URL                                       | Request Method |
| ----------------------------------------- | -------------- |
| http://127.0.0.1:8081/query/getPrediction | GET            |

#### 请求参数说明

| 请求参数 | 类型   | 必填 | 参数说明                                                     | 示例           |
| -------- | ------ | ---- | ------------------------------------------------------------ | -------------- |
| date     | String | true | 要查询的时间点，格式为`4位年份-月份-天 时:分:秒`（支持自动填充月份、天、时分秒前的0） | 2021-1-1 1:2:3 |

#### 返回参数说明

| 返回参数   | 参数类型 | 参数说明       |
| ---------- | -------- | -------------- |
| message    | String   | 预测情况       |
| prediction | String   | 预测出的人流量 |

#### 返回示例

##### 成功

```json
{
    "message": "预测成功",
    "prediction": "666"
}
```

##### 失败

```json
{
    "message": "请输入正确的时间，输入格式为uuuu-M-d H:m:s"
}
```
