-- INSERT bidlist
INSERT INTO bidlist (bid_list_id, account, ask, ask_quantity, benchmark, bid, bid_list_date, bid_quantity, book, commentary,
                   creation_date, creation_name, deal_name, deal_type, revision_date, revision_name, security, side, source_list_id,
                   status, trader, type)
values (null, 'Account1', 20.50, 5.5, 'Bencjmark1', 7.5, CAST('2015-05-25 15:32:06.427' AS DateTime), 10.00, 'Book1', 'Commentary1',
        CAST('2015-07-19 20:00:00.000' AS DateTime), 'CreationName1', 'DealName1', 'DealType1', CAST('2015-09-15 13:30:00.427' AS DateTime),
        'RevisionName1', 'Security1', 'Side1', 'SourceListId1', 'Status1', 'Trader1', 'Type1'),
       (null, 'Account2', 42.50, 5.5, 'Bencjmark2', 17.5, CAST('2017-05-25 15:32:06.427' AS DateTime), 20.00, 'Book2', 'Commentary2',
        CAST('2017-07-19 20:00:00.000' AS DateTime), 'CreationName2', 'DealName2', 'DealType2', CAST('2017-09-15 13:30:00.427' AS DateTime),
        'RevisionName2', 'Security2', 'Side2', 'SourceListId2', 'Status2', 'Trader2', 'Type2'),
       (null, 'Account3', 70.00, 5.5, 'Bencjmark3', 36.5, CAST('2018-06-25 15:32:06.427' AS DateTime), 80.00, 'Book3', 'Commentary3',
        CAST('2018-05-13 20:00:00.000' AS DateTime), 'CreationName3', 'DealName3', 'DealType3', CAST('2018-10-15 20:00:00.427' AS DateTime),
        'RevisionName3', 'Security3', 'Side3', 'SourceListId3', 'Status3', 'Trader3', 'Type3'),
       (null, 'Account4', 89.00, 21.5, 'Bencjmark4', 24.5, CAST('2019-07-25 15:32:06.427' AS DateTime), 53.00, 'Book4', 'Commentary4',
        CAST('2019-06-13 20:00:00.000' AS DateTime), 'CreationName4', 'DealName4', 'DealType4', CAST('2019-11-15 20:00:00.427' AS DateTime),
        'RevisionName4', 'Security4', 'Side4', 'SourceListId4', 'Status4', 'Trader4', 'Type4'),
       (null, 'Account5', 59.00, 13.5, 'Bencjmark5', 29.5, CAST('2020-08-25 15:32:06.427' AS DateTime), 55.00, 'Book5', 'Commentary5',
        CAST('2020-07-13 20:00:00.000' AS DateTime), 'CreationName5', 'DealName5', 'DealType5', CAST('2020-12-15 20:00:00.427' AS DateTime),
        'RevisionName5', 'Security5', 'Side4', 'SourceListId5', 'Status5', 'Trader5', 'Type5');

-- INSERT curvepoint
INSERT INTO curvepoint(id, as_of_date, creation_date, curve_id, term, value)
values (null, CAST('2017-01-07 05:00:00.000' AS DateTime), CAST('2017-01-07 14:00:00.000' AS DateTime), 1, 13.5, 10.5),
       (null, CAST('2017-02-02 09:00:00.000' AS DateTime), CAST('2017-02-02 16:00:00.000' AS DateTime), 2, 30.5, 24.5),
       (null, CAST('2017-03-14 08:00:00.000' AS DateTime), CAST('2017-03-14 15:00:00.000' AS DateTime), 3, 53.5, 40.5),
       (null, CAST('2017-04-20 08:00:00.000' AS DateTime), CAST('2017-04-04 16:00:00.000' AS DateTime), 4, 50.5, 25.5),
       (null, CAST('2017-05-05 05:00:00.000' AS DateTime), CAST('2017-05-05 14:00:00.000' AS DateTime), 5, 17.5, 19.5);

-- INSERT rating
INSERT INTO rating (id,fitch_rating,moodys_rating,order_number,sandp_rating)
values (null, 'Fitch1', 'Moddys1', 0132654, 'Sandprating1'),
       (null, 'Fitch2', 'Moddys2', 0565453, 'Sandprating2'),
       (null, 'Fitch3', 'Moddys3', 0474545, 'Sandprating3'),
       (null, 'Fitch4', 'Moddys4', 0765624, 'Sandprating4'),
       (null, 'Fitch5', 'Moddys5', 8455045, 'Sandprating5');

-- INSERT rulename
INSERT INTO rulename (id,description,json,name,sql_part,sql_str,template)
values (null, 'Description1', '{ "message" : "message breaks json1" }', 'Name1', 'SqlPart1', 'SqlStr1', 'Template1'),
       (null, 'Description2', '{ "message" : "message breaks json2" }', 'Name2', 'SqlPart2', 'SqlStr2', 'Template2'),
       (null, 'Description3', '{ "message" : "message breaks json3" }', 'Name3', 'SqlPart3', 'SqlStr3', 'Template3'),
       (null, 'Description4', '{ "message" : "message breaks json4" }', 'Name4', 'SqlPart4', 'SqlStr4', 'Template4'),
       (null, 'Description5', '{ "message" : "message breaks json5" }', 'Name5', 'SqlPart5', 'SqlStr5', 'Template5');

-- INSERT trade
INSERT INTO trade (trade_id,account,benchmark,book,buy_price,buy_quantity,sql_part,sql_str,template,creation_date,creation_name,
                   deal_name,deal_type,revision_date,revision_name,security,sell_price,sell_quantity,side,source_list_id,status,
                   trade_date,trader,type)
values (null,'Account1','Benchmark1','Book1',5.7,8.5,'SqlPart1','SqlStr1','Template1', CAST('2017-02-02 09:00:00.000' AS DateTime),'CreationName1',
        'DealName1','DealType1', CAST('2017-02-02 09:00:00.000' AS DateTime),'RevisionRame1','Security1',10.11,18.5,'Side1','SourceListId1','Status1',
        CAST('2017-02-02 09:00:00.000' AS DateTime),'Trader1','Type1'),
       (null,'Account2','Benchmark2','Book1',14.7,18.5,'SqlPart2','SqlStr2','Template2', CAST('2017-03-02 09:00:00.000' AS DateTime),'CreationName2',
        'DealName2','DealType2', CAST('2017-03-02 09:00:00.000' AS DateTime),'RevisionRame2','Security2',10.00,48.5,'Side1','SourceListId2','Status2',
        CAST('2017-02-02 09:00:00.000' AS DateTime),'Trader2','Type2'),
       (null,'Account3','Benchmark3','Book3',34,5,'SqlPart3','SqlStr3','Template3', CAST('2017-04-02 09:00:00.000' AS DateTime),'CreationName3',
        'DealName3','DealType3', CAST('2017-04-02 09:00:00.000' AS DateTime),'RevisionRame3','Security3',13,22,'Side3','SourceListId3','Status3',
        CAST('2017-02-02 09:00:00.000' AS DateTime),'Trader3','Type3'),
       (null,'Account4','Benchmark4','Book4',77,80.62,'SqlPart4','SqlStr4','Template4', CAST('2017-05-02 09:00:00.000' AS DateTime),'CreationName4',
        'DealName4','DealType4', CAST('2017-05-02 09:00:00.000' AS DateTime),'RevisionRame4','Security4',58,45,'Side4','SourceListId4','Status4',
        CAST('2017-02-02 09:00:00.000' AS DateTime),'Trader4','Type4'),
       (null,'Account5','Benchmark5','Book5',89.49,77.44,'SqlPart5','SqlStr5','Template5', CAST('2017-02-02 09:00:00.000' AS DateTime),'CreationName5',
        'DealName5','DealType5', CAST('2017-06-02 09:00:00.000' AS DateTime),'RevisionRame5','Security5',65,95.5,'Side5','SourceListId5','Status5',
        CAST('2017-05-02 09:00:00.000' AS DateTime),'Trader5','Type5');

-- INSERT users
INSERT INTO users (id,fullname,password,role,username)
values (null, 'Abel Kiss', '$2y$10$nsJaBoL3eKyQyyPDdLQOke3BrbEPoHgBRBrbzszUtWXlZVz03Uw32', 'USER', 'abel'),
       (null, 'Unknown Unknown', '$2y$10$88ct8lPEf2bymXwN5NzGferDjsTG89jWA6UyQL8iLhVKShFCaKYMC', 'USER', 'test');
