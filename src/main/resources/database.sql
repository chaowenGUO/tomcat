create table if not exists productItem (image int primary key,
                                        description text not null,
                                        price double not null);
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
