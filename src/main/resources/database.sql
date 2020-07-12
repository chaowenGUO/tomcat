create table productItem (image int primary key,
                                        description text not null,
                                        price decimal(6,2) not null);
insert into productItem values (9543, '好先生同款墨镜孙红雷偏光男士太阳镜韩明星', 97.50),
                               (9532, '陌森太阳眼镜男女2016偏光定制驾驶近视', 518.70),
                               (9521, '帕莎Prsr太阳镜女偏光镜潮范冰冰同款女', 624.00),
                               (9510, '变色眼镜男女款半框太阳镜大框潮流防辐射防', 170.00),
                               (9499, '新款男士偏光太阳镜日夜两用墨镜潮运动开车', 551.00),
			       (10192, '新生儿婴儿提篮式安全座椅汽车用车载儿童安', 882.00),
			       (10181, 'REEBABY汽车儿童安全座椅ISOFI', 1344.00),
			       (10170, 'REEBABY儿童安全座椅9个月-12岁', 1216.00),
			       (10159, '好孩子汽车儿童安全座椅goodbaby9', 1199.40),
			       (10148, '惠尔顿儿童安全座椅isofix硬接口汽车', 1993.60);
create table productUnit (image int primary key,
                                        price decimal(5,2) not null,
                                        description text not null,
                                        deal int not null,
                                        review int not null);
insert into productUnit values (7058, 799.20, 'MAXFEEL休闲男士手包真皮手拿包大容量信封包手抓包夹包软韩版潮', 16, 14),
                               (7047, 511.20, '宾度 男士手包真皮大容量手拿包牛皮个性潮男包手抓包软皮信封包', 49, 18),
                               (7036, 448.20, '唯美诺新款男士手包男包真皮大容量小羊皮手拿包信封包软皮夹包潮', 34, 16),
                               (7025, 411.60, '英伦邦纹男士手包牛皮大容量真皮手拿包手抓包双拉链商务正品软皮', 25, 19),
                               (7014, 157.25, '劳迪莱斯男士手包休闲手拿包牛皮大容量钱包男包软面小包包手抓包', 17, 16),
                               (7003, 268.20, '帕朗尼男士手拿包真皮手包商务休闲头层牛皮软牛皮大容量休闲钱包', 5, 19),
                               (6992, 233.40, '编织手包手拿包男信封大容量手抓包真皮韩版潮商务休闲牛皮男包', 8, 19),
                               (6981, 952.00, '犟牛男士手包真皮手拿包头层牛皮商务大容量手抓包软皮夹包信封包', 20, 16);
create table productReview (description text not null,
                                          "date" date not null,
                                          "user" text not null);
insert into productReview values ('不错的购物，包装很精细，布料也不错舒服，因为之前一直购买品牌装，很好的一次网购，生完宝宝刚刚一个多月我一百五十斤穿2XL不错', '2016-08-10', '哀****莉'),
                                 ('面料很好，大小也刚刚好，本来买之前还担心，犹豫，等货到了，比我心中想象的好很多，大家放心购买，很满意的购物，以后还会来??', '2016-08-23', '贤*闲'),
                                 ('商品： 宝贝料子和手感都不错，是值这价，卖家很贴心的送了内衣带，5分好评是必须的。喜欢的可以 下手了', '2016-08-16', '孤*****爱'),
                                 ('衣服收到了，给朋友买的，真心不错，料子也特别好，穿起来特别有气质，跟模特穿的效果差不多。。。好评，好评', '2016-08-19', '哀****莉'),
                                 ('衣服不错，手感不错，试穿很有型，真心的喜欢，谢谢老板', '2016-07-19', '孤*****爱'),
                                 ('喜欢(⊙o⊙)哦，美美哒，值得一试！！！宽松雪纺，很舒服', '2016-08-10', '这****0'),
                                 ('衣服已经收到，大小很合适，面料也可以，就是感觉衣服面料有一点厚，其他都还不错，款式也好看，希望店家多出一点新款，以后回来光顾你们。?', '2016-07-10', 'y****心'),
                                 ('很漂亮的衣服，料子有垂感，我很满意，老公也说好呢', '2016-07-27', '阿*丶'),
                                 ('真不错，已经迫不及待的穿上了，朋友们都说好，挺有气质的，就是上衣稍微有点肥，不过肉肉被藏起来了！有看上的赶紧下单吧！', '2016-08-12', '名*****_'),
                                 ('质量是真的不错，适合个子高一点的美眉穿，很漂亮?', '2016-08-03', '安**°'),
				 ('再买的时候纠结了好久，最后还是选择在这里购买，一次满意的网购，让我对网购有了希望，或刚收到，因觉得物超所值，就迫不及待拍了照片，那个玻璃的声音非常脆，我个人觉得挺好的，值得拥有！', '2016-08-08', '这****0'),
				 ('服务：不错', '2016-08-20', '醉**心'),
				 ('强烈推荐，我的天，质量也太好了，让我情何以堪，大爱啊。说说我的体格吧，162.105斤，腰围大概是22，穿m码刚刚好。快递那么快，特地跟店家说10号要外出店家也尽心备注的很好。真的特别感动，我收藏店铺了，下次还来你们家。衣服比雪纺舒服，薄薄的，现在穿刚好，我配了高跟鞋和小白鞋都很完美。店家还送了无痕肩带，真的太细心了，给一百个好评，赞。衣服我看了没有线头，薄薄的。不说了，我赶紧发给我姐让她看看。', '2016-08-17', '安**°'),
				 ('此用户没有填写评论！', '2016-07-21', '乱**型'),
				 ('衣服质量真心不错很厚实，上身效果很棒哦，穿起来很舒服，很洋气。价格也很合理……赞赞赞', '2016-08-19', '丶***眠'),
				 ('衣服裤子都很合身，同事笑我说这是我买得最好看最时尚的一套衣服，衣服上身效果也不错，很是满意，还送了隐形带，正好用上，质量很好穿着凉快，唯一不足的就是洗了后需要烫一下才不皱，不过不影响衣服的美，很满意。上传两张没P的照片', '2016-08-08', '乱**型'),
				 ('不好意思好久才来评价  质量好  上身效果好  主要有胖子穿的漂亮衣服啦   赞赞赞', '2016-07-11', 'l******1'),
				 ('网购以来为数不多的质量好到超乎意料，穿上特别舒服，大小也正合适，超赞！这个店里值得好好逛逛！', '2016-07-10', '盗**记'),
				 ('宝贝面料很舒服，一字领也非常美，说实话是充上衣买的！犹豫了很长时间才下定决心的，很喜欢，很满意！喜欢就是最值的！', '2016-07-24', '贤*闲');
