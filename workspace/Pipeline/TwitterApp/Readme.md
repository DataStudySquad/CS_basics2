# TwitterApp
- Social media app
- Raw event (json) from stream source
- Raw event sample as below:
```json
// some json
```
- Feature
    - Timeline
    - follower-following
    - re-tweet
    - hashtag

## Part 1) Data Model Design
```sql

# user
CREATE TABLE user IF NOT EXISTS user (
    user_id VARCHAR(30) PRIMARY KEY,
    user_name VARCHAR(20),
    created_on TIMESTAMP,
    status int,
    email VARCHAR(20),
    city_id VARCHAR(10),
    validated BOOLEAN
)

# follower
CREATE TABLE follower IF NOT EXISTS follower (
    user_follower_id VARCHAR(30) PRIMARY KEY,
    user_id VARCHAR(30),
    follower_id VARCHAR(30)
)

# following
CREATE TABLE following IF NOT EXISTS following (
    user_following_id VARCHAR(30)  PRIMARY KEY,
    user_id VARCHAR(30),
    following_id VARCHAR(30)
)

# tweet
CREATE TABLE tweet IF NOT EXISTS tweet (
    tweet_id VARCHAR(30)  PRIMARY KEY,
    author_id VARCHAR(30),
    created_on TIMESTAMP,
    last_modified TIMESTAMP
)

# content
CREATE TABLE content IF NOT EXISTS content (
    content_id PRIMARY KEY,
    tweet_id VARCHAR(30),
    retweet_id VARCHAR(30),
    content VARCHAR(100),
    retweet BOOLEAN
)

# ReTweet
CREATE TABLE ReTweet IF NOT EXISTS ReTweet(
    retweet_id VARCHAR(30) PRIMARY KEY,
    tweet_id VARCHAR(30),
    user_id VARCHAR(30),
    retweet_on TIMESTAMP

# HashTag

# Geolocation
)
```

## Part 2) ETL
- Batch
- Stream

## Part 3) SQL
```sql
```

## Part 4) Prod Sense & DashBoard

## Ref
- DB schema
    - http://140dev.com/free-twitter-api-source-code-library/twitter-database-server/mysql-database-schema/
- https://www.researchgate.net/figure/Twitter-data-model-and-flow_fig3_266369090
- https://www.linkedin.com/pulse/system-design-twitter-avinash-anantharamu/
- https://medium.com/@narengowda/system-design-for-twitter-e737284afc95
- https://aaronice.gitbook.io/system-design/system-design-problems/design-twitter
- https://www.8bitmen.com/what-database-does-twitter-use-a-deep-dive/