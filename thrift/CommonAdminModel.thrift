namespace java com.googlecode.bootstrapx.model

enum OrderBy {
    DESC = 0,
    ASC = 1
}

enum OperateType {
	ADD = 0,
	UPDATE = 1,
	REMOVE = 2
}

struct User {
    1: i32 id;
    2: string email = '';
    3: string name = '';
    4: string password = '';
    5: i32 creatorId;
    6: i32 updaterId;
    7: i64 createTime = 0;
    8: i64 updateTime = 0;
    9: i32 status = 1;
}

struct Feature {
    1: i32 id;
    2: string name = '';
    3: string link = '';
    4: i32 sequence = 0;
}

struct SubSystem {
    1: i32 id;
    2: string name = '';
    3: string image = '';
    4: i32 sequence = 0;
    5: list<Feature> featureList;
}

struct SystemLog {
    1: i64 id;
    2: i64 createTime = 0;//在xxx时间
    3: string ip = '';//来自IP的
    4: i32 userId = 0;//xx用户
    5: string target;//对xxx表格
    6: string action;//执行  添加/删除/更新/登录/退出  操作
    7: string description;//详细描述:
}

struct Privilege {
    1: i32 id;
    2: i32 parentId;
    3: string name;
    4: string link;
    5: i64 createTime = 0;
    6: i64 updateTime = 0;
    7: i32 status = 1;
}
struct UserPrivilege {
    1: i32 userId;
    2: i32 privilegeId;
    3: i64 createTime = 0;
    4: i64 updateTime = 0;
    5: i32 status = 1;
}