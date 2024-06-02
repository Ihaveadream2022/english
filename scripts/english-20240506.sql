/*
 Navicat Premium Data Transfer

 Source Server         : VirtualBox-192.168.10.103
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : 192.168.10.103:3306
 Source Schema         : english

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 06/05/2024 01:43:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category` smallint UNSIGNED NULL DEFAULT 0 COMMENT 'category',
  `name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'lexical item name',
  `pronounce` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'pronounce',
  `common` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'common meaning',
  `noun` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'noun ',
  `noun_plural` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'noun plural',
  `noun_examples` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'noun examples',
  `verb` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'verb',
  `verb_past_tense` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'verb past tense',
  `verb_past_participle` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'verb past participle',
  `verb_present_participle` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'verb present participle',
  `verb_third_person_singular` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'verb third person singular',
  `verb_examples` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'verb examples',
  `adjective` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'adjective',
  `adjective_examples` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'adjective examples',
  `adverb` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'adverb',
  `adverb_examples` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'adverb examples',
  `conjunction` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conjunction',
  `conjunction_examples` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conjunction examples',
  `pronoun` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'pronoun',
  `pronoun_examples` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'pronoun examples',
  `preposition` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'preposition',
  `preposition_examples` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'preposition examples',
  `audio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'base64 audio',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `word`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'word' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, 0, 'drone', 'drōn', '无人机', 'n.无人机', 'drones', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (3, 0, 'draw', 'drô', '画,吸引,平局', 'n.抽签;抽奖;平局;和局;吸烟;抽彩;不分胜负;有吸引力的人（或事物）;由抽签决定对手的比赛', NULL, 'The match ended in a draw', 'v.画;进行，作（比较或对比）;吸引;拉(动);（向某个方向）移动，行进;产生，引起，激起（反应或回应）;(用铅笔、钢笔或粉笔)描绘;提取;牵引;抽出;抽（烟）;抽（签、牌）;获取;描画;拖(动);拔出;使说出;以平局结束', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (4, 0, 'resupply', 'ˈrēsəˌplī', '补给', NULL, NULL, NULL, 'v.补给', 'resupplied', 'resupplied', 'resupplying', 'resupplies', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (5, 0, 'sailor', 'ˈsālər', '水手', 'n.水手', 'sailors', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (6, 0, 'abstain', 'əbˈstān', '戒', NULL, NULL, 'abstain from consumption of alcoho\r\nabstaining from sex', 'vi. (投票时)弃权; 戒(性生活, 饮酒);', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (7, 1, 'Manila', 'məˈnilə', '马尼拉', 'n. 马尼拉', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (9, 0, 'shoreline', 'ˈSHôrˌlīn', '海岸线', 'n.海岸线', 'shorelines', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (10, 0, 'reef', 'rēf', '礁石', 'n.礁石', 'reefs', NULL, NULL, NULL, NULL, NULL, 'reefs', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (11, 0, 'Philippine', 'ˈfiləˌpēn', '菲律宾的', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'adj.菲律宾的;菲律宾人的;', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (12, 1, 'philippines', 'ˈfiləˌpēns', '菲律宾', 'n.菲律宾', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (13, 0, 'reliant', '/rɪˈlaɪənt/', '依赖他人的', NULL, NULL, 'over-reliant 过度依赖的 Supply chains have made the US economy over-reliant on the Chinese economy in the past.', NULL, NULL, NULL, NULL, '', NULL, 'adj.依靠的;依赖性的', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (14, 0, 'decouple', '/diːˈkʌpl/', '脱钩', NULL, NULL, 'As US supply chains decouple from China... 随着美国供应链与中国脱钩...', 'vt.使两事物分离; 隔断;', 'decoupled', 'decoupled', 'decoupling', 'decouples', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (15, 0, 'vessel', '/ˈvesl/', '船只', 'n.(盛液体的)容器;(人或动物的)血管;器皿;(植物的)导管;脉管;轮船;大船', 'vessels', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (16, 0, 'exclusive', 'ikˈsklo͞osiv', '独家报道', 'n.独家新闻; 独家报道; 独家专文', NULL, 'exclusive economic zone', NULL, NULL, NULL, NULL, NULL, NULL, 'adj.独家的; 专属的; 排斥的; (个人或集体)专用的，专有的，独有的，独占的; 不包括;高级的;豪华的;排外的;高档的;不愿接收新成员(尤指较低社会阶层)的', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (17, 0, 'integral', 'ˈin(t)əɡrəl', '不可或缺的', 'n.积分;整体', NULL, 'The Paracels and the Spratlys are an integral part of chinese territory\\r\\n西沙群岛和南沙群岛是中国领土不可分割的一部分', NULL, NULL, NULL, NULL, NULL, NULL, 'adj.完整的;不可或缺的;必需的;完备的;作为组成部分的', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (18, 2, 'PPL', NULL, '人', 'n.人', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (19, 0, 'mess', 'mes', '弄乱,混乱', 'n.混乱;困境;（军队的）食堂，餐厅;(组织欠佳等导致的)麻烦;许多;杂乱;肮脏;（狗、猫等的）粪便;不整洁;不整洁(或邋遢、不修边幅)的人;遇到严重问题且精神状态不佳的人', NULL, '[mess up 搞砸了]: \r\n-Lebron messed up his handshake with Beasley.\r\n-2024年4月26库里获得起亚关键时刻球员奖. 巴克利吐槽这奖项胡搞, 意思是勇士和库里这赛季表现都一般. 随后奥尼尔采访库里说调侃道: 我替巴克利的胡搞向你道歉', 'v.弄乱;弄脏;使不整洁;随地便溺', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (20, 0, 'tip', 'tɪp', '小费', 'n.小费;', NULL, 'big tipper 小费给得多的人.奥尼尔给饭店服务员4000美金小费', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (21, 0, 'stroke', 'strōk', '抚摸', NULL, NULL, '[摸头]:\r\n-Girl mistook a stranger for an angel... She then strokes his head with her hand.\r\nhttps://youtube.com/shorts/Po_zCGdLO7w?si=2XSP5JiZLrsWy3F6', 'vt.抚摸; 摩挲(头, 脸, 皮毛);', 'stroked', 'stroked', 'stroking', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (22, 3, 'Jesus', 'ˈdʒiːzəs', '耶稣', 'n.耶稣', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (23, 0, 'bodygurd', NULL, '保镖', 'n.保镖', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (24, 0, 'deaf', 'def', '聋的', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'adj.聋的', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (25, 0, 'alternative', 'ôlˈtərnədiv', '可供替代的', 'n.可供选择的事物', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'adj.可供替代的;非传统的;另类的', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (28, 0, 'shoal', 'SHōl', '浅滩', 'n.浅滩;鱼群;水下沙洲', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (30, 0, 'panic', '/ˈpænɪk/', '慌', 'n.恐慌;惊恐;惶恐不安;人心惶惶的局面', 'panics', NULL, 'v.(使)惊慌，惊慌失措', 'panicked', 'panicked', 'panicking', 'panics', NULL, 'adj.恐慌的;过度的;(恐慌心理)没来由的;牧人之神的', 'don\'t panic 别慌', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (31, 0, 'billboard', '/ˈbɪlbɔːrd/', '广告牌', 'n.(大幅)广告牌', 'billboards', NULL, 'v.宣传', 'billboarded', 'billboarded', 'billboarding', 'billboards', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (32, 0, 'stuff', '/stʌf/', '东西', 'n.（事物名称不详、无关紧要或所指事物明显时用）东西，物品，玩意儿;原料，材料;作品，作品（或演讲、乐曲等）的内容;基本特征，特质，根本;<口>能力，本事;物料，素材;行李，装备;(泛指)活儿，话，念头;家具;<英口><旧>无用的东西，废物;货色，财产;<口>毒品，麻醉剂（尤指海洛因）;要素，基本的东西，特质，本质; <英>料子，毛料，呢绒;<美俚>赃物，走私货(尤指走私的威士忌酒);(发射出的)枪弹，炮弹;<美口><篮>灌篮，扣篮;<美口><棒>(投球手等使球旋转或呈曲线飞行等的)制球能力，制球', NULL, NULL, 'v.把…塞进(或填进);使充满, 使充斥;填满，装满;<俚>处理掉, 丢掉;（使）吃撑，吃足，吃得过饱;给(皮革)加脂;<英俚> <忌>与(女人)性交;给……装馅;<美俚>诓骗, 作弄;<英口>(在比赛中)轻易击败(对手);在(蔬菜、鸡等)里填入(另外一种食物);<美>把伪票投入(票箱);制作（动物）标本', 'stuffed', 'stuffed', 'stuffing', 'stuffs', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (33, 6, 'belt', '/belt/', '带状物体', 'n.带状物体. 例如: 腰带, 传送带, 皮带.', 'belts', 'Belt and Road 一带一路', 'v.飞驰;狠打;飞奔;猛击;用带系住', 'belted', 'belted', 'belting', 'belts', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (34, 0, 'collar', '/ˈkɑːlər/', '衣领', 'n.衣领;领子;(动物，尤指狗的)颈圈;(管子或机器部件的)圈、箍', 'collars', 'blue-collar worker 蓝领工人\nwhite-collar worker 白领工人', 'vt.抓住;揪住;捉住;逮住;拦住(某人以与其)谈话', 'collared', NULL, 'collaring', 'collars', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (35, 0, 'betray', '/bɪˈtreɪ/', '背叛', NULL, NULL, NULL, 'vt.背叛(人,组织,国家,信仰);辜负;不忠;', 'betrayed', 'betrayed', 'betraying', 'betrays', 'betray the Party 叛党', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (36, 0, 'big deal', NULL, '大事; 没啥了不起', 'n.大事; 要事', NULL, '本意: 大事；了不起\n口语中常用作反讽：这有啥了不起的', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (37, 0, 'volatile', '/ˈvɑːlətl/', NULL, 'n.<罕>有翅动物;易挥发物', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'adj.不稳定的;<计>易失的;易挥发的，易发散的;爆发性的，爆炸性的;易变的，无定性的，无常性的;短暂的，片刻的;活泼的，轻快的，无忧虑的;暴躁的;易引起的;易恶化的;可能急剧波动的;<古>迅速游移的', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES (38, 0, 'infrastructure', '/ˈɪnfrəstrʌktʃər/', NULL, 'n.(国家或机构的)基础设施，基础建设', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'key',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'name',
  `created_at` datetime NULL DEFAULT NULL COMMENT 'create time',
  `updated_at` datetime NULL DEFAULT NULL COMMENT 'update time',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT 'status',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`key` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'role' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (4, 'Editor', 'editor', '2024-04-15 18:16:49', '2024-04-15 18:26:34', 1);

-- ----------------------------
-- Table structure for role_assignment
-- ----------------------------
DROP TABLE IF EXISTS `role_assignment`;
CREATE TABLE `role_assignment`  (
  `user_id` bigint NOT NULL COMMENT 'user ID',
  `role_id` bigint NOT NULL COMMENT 'role ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `role_assignment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_assignment_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'association between users and roles' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_assignment
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT 'role ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'username',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'email',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'phone number',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'password',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'avatar',
  `last_login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'last login iP',
  `gender` tinyint NULL DEFAULT 0 COMMENT 'gender',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT 'last login time',
  `created_at` datetime NULL DEFAULT NULL COMMENT 'create time',
  `updated_at` datetime NULL DEFAULT NULL COMMENT 'update time',
  `status` tinyint NULL DEFAULT 0 COMMENT 'status',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'user' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, NULL, 'admin', 'admin@english.com', '18201553454', '$2a$10$CbIhUh/eTFTTNWGs0tqIbefArGGHqSTzc8q42VAp8ZPswbX7RdzNa', NULL, '127.0.0.1', 0, '2024-05-03 18:16:54', '2024-03-15 12:11:08', '2024-04-15 14:03:12', 0);

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'user',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'operation name',
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP adress',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'operation location',
  `browser` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'browser type',
  `os` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'operation system',
  `operation_time` datetime NULL DEFAULT NULL COMMENT 'operation time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'user operations' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
