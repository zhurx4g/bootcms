namespace java com.googlecode.bootstrapx.model

struct Config {
    1: string key;
    2: string value;
    3: string name;
    4: string description;
    5: i32 order;
}

struct Category {
    1: i32 id;
    2: i32 parentId;
    3: string name;
    4: i32 sequence;
    5: i32 weight;
    6: string icon;
    7: string image;
    8: string description;
    9: i32 creatorId = 0;
    10: i32 updaterId = 0;
    11: i64 createTime = 0;
    12: i64 updateTime = 0;
    13: string link = '';
    14: i32 status = 1;
}

struct Navigate {
    1: i32 id;
    2: i32 parentId;
    3: string name;
    4: i32 sequence;
    5: i32 weight;
    6: string icon;
    7: string image;
    8: string description;
    9: i32 creatorId = 0;
    10: i32 updaterId = 0;
    11: i64 createTime = 0;
    12: i64 updateTime = 0;
    13: string link = '';
    14: i32 status = 1;
    15: i32 count = 0;
}

struct FriendLink {
    1: i32 id;
    2: string name;
    3: i32 sequence;
    4: i32 weight;
    5: string icon;
    6: string image;
    7: string description;
    8: i32 creatorId = 0;
    9: i32 updaterId = 0;
    10: i64 createTime = 0;
    11: i64 updateTime = 0;
    12: string link = '';
    13: i32 status = 1;
}