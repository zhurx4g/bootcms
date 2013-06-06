namespace java com.googlecode.bootstrapx.model

struct Config {
    1: string key;
    2: string value;
    3: string name;
    4: string description;
    5: i32 order;
}

struct Nav {
    1: i32 status;
    2: i64 createTime;
    3: i32 creatorId;
    4: i64 updateTime;
    5: i32 updaterId;
    6: i32 weight;
    7: i32 order;
    8: i32 id;
    9: i32 pid;
    10: string name;
    11: string link;
    12: string tips;
    13: string description;
}