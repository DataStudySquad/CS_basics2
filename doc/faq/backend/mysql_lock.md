# MySQL Lock

### 1) Mysql Lock


#### 全局鎖

 - Use cases : DB backup
 - All tables in DB become `read only` status

```sql
# sql

# SQL client 1
flush tables with read lock;

# SQL client 2 (will be locked)
INSERT INTO blog_db.authors(`id`,`email`,`name`,`url`, `create_time`, `update_time`)
 values
(100,  "bill@google.com", "bill", "" ,  now(), now()),
(101,  "jack@uber.com", "jack", "" , now(), now()),
(102, "yen@google.com", "yen", "" , now(), now());

# unlock
unlock tables
```

- Ref
	- https://www.xiaolincoding.com/mysql/lock/mysql_lock.html#%E8%A1%A8%E7%BA%A7%E9%94%81
	- https://medium.com/@martin87713/mysql-lock-55ca187e4af2


#### 表級鎖

- Types
	- 表鎖
	- 元數據鎖（MDL)
	- 意向鎖
	- AUTO-INC 鎖

- 讀讀分享、讀寫互斥、寫寫互斥

- 表鎖
	- 會話退出後, 會釋放所有表鎖
	- 避免在InnoDB表使用表鎖, 顆粒範圍太大
	- 屬於一次封鎖技術 ，如果我們一開始使用 lock 將後面用到的表都上鎖，`在釋放鎖之前我都只能訪問加鎖的表`，其餘都不行，好處是不會發生 Dead Lock（死鎖）
	- 開銷小，加鎖快，不會有死鎖，鎖定範圍大，發生鎖衝突機率高，併發低

- 表鎖類型
	- 讀鎖
		- 持有讀鎖可以讀表不能寫表
		- 允許多個線埕持有讀鎖
		- 其他線埕就算沒有給表讀鎖，也可以讀表
		- 其他線埕想給表上寫鎖會被卡住，直到鎖釋放
	- 寫鎖
		- 持有寫鎖的線埕可以讀也可以寫
		- 只有持有寫鎖的線埕才可以訪問表，其他線埕會被卡住等待鎖釋放
		- 其他線埕想對表加鎖都會被卡住，直到鎖釋放


```sql
# sql

use blog_db;

# SQL client 1
# 表级别的共享锁，也就是读锁；
lock tables authors read;

-- Can ONLY read tables which are LOCKED
-- mysql> select * from comment;
-- ERROR 1100 (HY000): Table 'comment' was not locked with LOCK TABLES


# 表级别的独占锁，也就是写锁；
lock tables comment write;

# unlock all locked table in current session
unlock tables

# SQL client 2
```

```sql
# sql
mysql> lock table products read, orders read; 
Query OK, 0 rows affected (0.00 sec)   
mysql> select * from products where id = 100; 
  
mysql> select * from orders where id = 200;  
 
mysql> select * from users where id = 300; 
ERROR 1100 (HY000): Table 'users' was not locked with LOCK TABLES   
mysql> update orders set price = 5000 where id = 200; 
ERROR 1099 (HY000): Table 'orders' was locked with a READ lock and can't be updated
   
mysql> unlock tables; Query OK, 0 rows affected (0.00 sec)
```

- 元数据锁（MDL)
	- 當對DB操作時, DB會自動加上元數據鎖
		- CURD 操作: MDL讀鎖
		- 對表結構變更操作: MDL寫鎖
	- ㄧ般不需要顯式使用元數據鎖

- 意向鎖
	- 在使用 InnoDB引擎的表裡對某些記錄加上 "共享鎖定"之前，需要先在表格層級加上一個 意向共享鎖定"
	- 在使用 InnoDB引擎的表裡對某些紀錄加上“獨佔鎖”之前，需要先在表格級別加上一個 “意向獨佔鎖”
	- 意向共享鎖, 意向獨佔鎖 是表級鎖，不會和行級的共享鎖和獨佔鎖發生衝突
	- 而且意向鎖之間也不會發生衝突，只會和共享表鎖（lock tables ... read ）和獨佔表鎖（lock tables ... write）發生衝突
	- so, 意向鎖的目的是為了快速判斷表裡是否有記錄被加鎖。
	- 表鎖, 行鎖皆滿足以下:
		讀讀分享、讀寫互斥、寫寫互斥

- AUTO-INC
	- 表裡的主鍵通常都會設定成自增的，這是透過對主鍵欄位聲明 AUTO_INCREMENT 屬性來實現的。
	之後可以在插入資料時，可以不指定主鍵的值，資料庫會自動給主鍵賦值遞增的值，這主要是透過 AUTO-INC 鎖定實現的。
	- AUTO-INC鎖定是特殊的表鎖機制，鎖定不是再一個交易提交後才釋放，而是再執行完插入語句後就會立即釋放。在插入資料時，會加上一個表格層級的 AUTO-INC 鎖定，然後為被 AUTO_INCREMENT 修飾的欄位賦值遞增的值，等插入語句執行完成後，才會將 AUTO-INC 鎖定釋放掉。


#### 行級鎖

- Types:
	- Record Lock, Gap Lock, Next-Key Lock
	- Share Record Lock(共享鎖), Exclusive Record Lock(獨佔鎖) (根據阻擋層級)
	- 行鎖在併發處理上有優勢，但因行鎖粒度小，每次取鎖、釋放鎖要做的事情比表鎖多，開銷較表鎖大，也較容易發生死鎖
	- 讀讀分享、讀寫互斥、寫寫互斥
	- 開銷大，加鎖慢，會有死鎖，鎖定範圍小，發生鎖衝突機率低，併發高

- InnoDB 引擎是支援行級鎖定的，而 MyISAM 引擎並不支援行級鎖定


- Record Lock
	- 針對該行的cluster index上鎖，即使這張表沒有設定任何index，InnoDB也會設定一個隱藏的index(hidden clustered index)，並為此index加鎖
	- Record Lock根據阻擋層級也可以分為Share Record Lock、Exclusive Record Lock
	- 紀錄鎖都是加在索引上，就算表沒有建立索引，DB也會創建隱式索引
	- 如果where條件沒有加在index, 則會全表掃描

```sql
mysql> UPDATE accounts SET level = 100 WHERE id = 5;
```

- Gap Lock
	- 這種鎖是為了避免，同一個 transaction 中，連續兩次讀取結果不一樣
	- 間隙鎖是一種加在兩個索引之間的鎖，或是第一個索引之前，或最後一個索引之後的間隙，又叫做範圍鎖 （Range Locks
	- 可以跨一個索引或是多個索引甚至是空的，可以防止其他 transaction 在這個範圍內插入或是修改紀錄，保證兩次讀取這個範圍的紀錄不會改變，防止幻讀（兩次讀取結果不同）現象發生。

- Next-Key Lock
	- Next-Key Lock概念上就等於Record Lock + Gap Lock，會建立一個Index Gap避免其他事務在這gap插入資料，也在Index本身加上鎖。雖說Next-Key Lock=Record Lock+Gap Lock，但Next-Key Lock建立的Gap其實是前開後閉的，


```sql
# sql

# SQL client 1
# 对读取的记录加共享锁
# select * from accounts where id = 5 lock in share mode;
select * from authors where id = 1 lock in share modes;


# 对读取的记录加独占锁
select * from authors for update;

# SQL client 2
```

### 2) Dead Lock

- 死鎖根本原因是有兩個或多個事務加鎖順序不一致導致

- Examples:
	- https://blog.twjoin.com/%E9%8E%96-lock-%E7%9A%84%E4%BB%8B%E7%B4%B9%E8%88%87%E6%AD%BB%E9%8E%96%E5%88%86%E6%9E%90-19833c18baab

### 3) How Modern DB deal with concurrence ?

- Types
 	- 2PL (2 phase locking)
 	- MVCC (Multiversion concurrency control) (多版本並行控制)

- Ref
	- https://blog.twjoin.com/%E9%8E%96-lock-%E7%9A%84%E4%BB%8B%E7%B4%B9%E8%88%87%E6%AD%BB%E9%8E%96%E5%88%86%E6%9E%90-19833c18baab
 	- https://www.fanyilun.me/2017/04/20/MySQL%E5%8A%A0%E9%94%81%E5%88%86%E6%9E%90/?source=post_page-----19833c18baab--------------------------------

### 4) InnoDB VS MyIsam

- 在 MySQL 5.1 及之前的版本中，MyISAM 是預設的儲存引擎，而在 MySQL 5.5 版本之後，預設使用 InnoDB 儲存引擎。

- MyISAM 不支援行級鎖，換句話說，MyISAM 會對整張表加鎖，而不是針對行。 同時，MyISAM 不支援事務和外鍵。 MyISAM 可被壓縮，儲存空間較小，而且 MyISAM 在篩選大量資料時非常快

- InnoDB 是事務型引擎，當交易異常提交時，會被回滾。 同時，InnoDB 支援行鎖。 此外，InnoDB 需要更多儲存空間，會在記憶體中建立其專用的緩衝池用於高速緩衝資料和索引。 InnoDB支援自動崩潰恢復特性。

- https://learn.lianglianglee.com/%E6%96%87%E7%AB%A0/%E9%9D%A2%E8%AF%95%E6%9C%80%E5%B8%B8%E8%A2%AB%E9%97%AE%E7%9A%84%20Java%20%E5%90%8E%E7%AB%AF%E9%A2%98.md