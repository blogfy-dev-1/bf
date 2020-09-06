
-- 应用服务器注册表
CREATE TABLE t_app_servers
(
  server_id character varying(32) NOT NULL, -- 主键
  server_ip character varying(32) NOT NULL, -- 服务器IP
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_app_servers_pkey PRIMARY KEY (server_id),
  CONSTRAINT t_app_servers_server_ip_key UNIQUE (server_ip)
);
COMMENT ON TABLE t_app_servers IS '服务器信息表';
COMMENT ON COLUMN t_app_servers.server_id IS '主键';
COMMENT ON COLUMN t_app_servers.server_ip IS '服务器IP';
COMMENT ON COLUMN t_app_servers.create_by IS '创建人';
COMMENT ON COLUMN t_app_servers.create_date IS '创建时间';
COMMENT ON COLUMN t_app_servers.update_by IS '修改人';
COMMENT ON COLUMN t_app_servers.update_date IS '修改时间';

-- 用户基本信息表
CREATE TABLE t_user_base_info
(
  user_id integer NOT NULL, -- 用户ID
  nick_name character varying(100), -- 昵称
  sex character varying(1), -- 性别
  head_img_url_max character varying (100), -- 头像（大）
  head_img_url_medium character varying (100), -- 头像（中）
  head_img_url_min character varying (100), -- 头像（小）
  email character varying(200), -- 邮箱
  mobile_phone character varying(32), -- 手机号
  pwd character varying(200), -- 密码（MD5后再AES加密）
  blog_title character varying (20), -- 博客名称
  blog_sub_title character varying (100), -- 博客子标题
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_user_base_info_pk PRIMARY KEY (user_id)
);
COMMENT ON TABLE t_user_base_info IS '用户基本信息表';
COMMENT ON COLUMN t_user_base_info.user_id IS '用户ID';
COMMENT ON COLUMN t_user_base_info.nick_name IS '昵称';
COMMENT ON COLUMN t_user_base_info.sex IS '性别，M / F';
COMMENT ON COLUMN t_user_base_info.head_img_url_max IS '头像（大）';
COMMENT ON COLUMN t_user_base_info.head_img_url_medium IS '头像（中）';
COMMENT ON COLUMN t_user_base_info.head_img_url_min IS '头像（小）';
COMMENT ON COLUMN t_user_base_info.email IS '邮箱';
COMMENT ON COLUMN t_user_base_info.mobile_phone IS '手机号';
COMMENT ON COLUMN t_user_base_info.pwd IS '密码（MD5后再AES加密）';
COMMENT ON COLUMN t_user_base_info.blog_title IS '博客名称';
COMMENT ON COLUMN t_user_base_info.blog_sub_title IS '博客子标题';
COMMENT ON COLUMN t_user_base_info.create_by IS '创建人';
COMMENT ON COLUMN t_user_base_info.create_date IS '创建时间';
COMMENT ON COLUMN t_user_base_info.update_by IS '修改人';
COMMENT ON COLUMN t_user_base_info.update_date IS '修改时间';

-- 用户属性信息表
CREATE TABLE t_user_prop_info
(
  user_id integer NOT NULL, -- 用户ID
  total_score integer default 0, -- 总积分
  view_num integer default 0, -- 访问数
  agree_num integer default 0, -- 点赞数
  favorite_num integer default 0, -- 收藏数
  repeat_num integer default 0, -- 转发数
  discuss_num integer default 0, -- 评论数
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_user_prop_info_pk PRIMARY KEY (user_id)
);
COMMENT ON TABLE t_user_prop_info IS '用户属性信息表';
COMMENT ON COLUMN t_user_prop_info.user_id IS '用户ID';
COMMENT ON COLUMN t_user_prop_info.total_score IS '总积分';
COMMENT ON COLUMN t_user_prop_info.view_num IS '访问数';
COMMENT ON COLUMN t_user_prop_info.agree_num IS '点赞数';
COMMENT ON COLUMN t_user_prop_info.favorite_num IS '收藏数';
COMMENT ON COLUMN t_user_prop_info.repeat_num IS '转发数';
COMMENT ON COLUMN t_user_prop_info.discuss_num IS '评论数';
COMMENT ON COLUMN t_user_prop_info.create_by IS '创建人';
COMMENT ON COLUMN t_user_prop_info.create_date IS '创建时间';
COMMENT ON COLUMN t_user_prop_info.update_by IS '修改人';
COMMENT ON COLUMN t_user_prop_info.update_date IS '修改时间';

-- 用户Github信息表
CREATE TABLE t_user_github_info
(
  user_id integer NOT NULL, -- 用户ID
  id integer, -- Github ID
  login character varying(100), -- 登录账号
  avatar_url character varying(100), -- 头像URL
  html_url character varying(100), -- GitHub首页URL
  name character varying(100), -- 昵称
  company character varying(100), -- 公司
  location character varying(100), -- 地区
  email character varying(100), -- 邮箱
  access_token character varying(100), -- GitHub access token
  access_token_expire_time timestamp without time zone, -- access token 到期时间
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_user_github_info_pk PRIMARY KEY (user_id)
);
COMMENT ON TABLE t_user_github_info IS '用户Github信息表';
COMMENT ON COLUMN t_user_github_info.user_id IS '用户ID';
COMMENT ON COLUMN t_user_github_info.id IS 'Github ID';
COMMENT ON COLUMN t_user_github_info.login IS '登录账号';
COMMENT ON COLUMN t_user_github_info.avatar_url IS '头像URL';
COMMENT ON COLUMN t_user_github_info.html_url IS 'GitHub首页URL';
COMMENT ON COLUMN t_user_github_info.name IS '昵称';
COMMENT ON COLUMN t_user_github_info.company IS '公司';
COMMENT ON COLUMN t_user_github_info.location IS '地区';
COMMENT ON COLUMN t_user_github_info.email IS '邮箱';
COMMENT ON COLUMN t_user_github_info.access_token IS 'GitHub access token';
COMMENT ON COLUMN t_user_github_info.access_token_expire_time IS 'access token 到期时间';
COMMENT ON COLUMN t_user_github_info.create_by IS '创建人';
COMMENT ON COLUMN t_user_github_info.create_date IS '创建时间';
COMMENT ON COLUMN t_user_github_info.update_by IS '修改人';
COMMENT ON COLUMN t_user_github_info.update_date IS '修改时间';

-- 用户关注关系表
CREATE TABLE t_user_follows
(
  follow_id character varying(10) NOT NULL, -- 主键
  follower_from_user_id integer NOT NULL, -- 关注者（粉丝）
  follower_to_user_id integer NOT NULL, -- 被关注者
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_user_follows_pk PRIMARY KEY (follow_id)
);
COMMENT ON TABLE t_user_follows IS '用户关注关系表';
COMMENT ON COLUMN t_user_follows.follow_id IS '主键';
COMMENT ON COLUMN t_user_follows.follower_from_user_id IS '关注者（粉丝）';
COMMENT ON COLUMN t_user_follows.follower_to_user_id IS '被关注者';
COMMENT ON COLUMN t_user_follows.create_by IS '创建人';
COMMENT ON COLUMN t_user_follows.create_date IS '创建时间';
COMMENT ON COLUMN t_user_follows.update_by IS '修改人';
COMMENT ON COLUMN t_user_follows.update_date IS '修改时间';

-- 文章基本信息表
CREATE TABLE t_post_base_info
(
  post_id integer NOT NULL, -- 文章ID
  user_id integer NOT NULL, -- 发表者用户ID
  title character varying(500), -- 标题
  description character varying(1000), -- 简介
  tags character varying(100), -- 标签
  view_num integer default 0, -- 访问数
  agree_num integer default 0, -- 点赞数
  favorite_num integer default 0, -- 收藏数
  repeat_num integer default 0, -- 转发数
  discuss_num integer default 0, -- 评论数
  original_type integer, -- 创作类型，1：原创，2：转载
  reproduction_url character varying(200), -- 转载链接
  status integer, -- 状态，1：公开，2：私有，3：删除
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_post_base_info_pk PRIMARY KEY (post_id)
);
COMMENT ON TABLE t_post_base_info IS '文章基本信息表';
COMMENT ON COLUMN t_post_base_info.post_id IS '文章ID';
COMMENT ON COLUMN t_post_base_info.user_id IS '发表者用户ID';
COMMENT ON COLUMN t_post_base_info.title IS '标题';
COMMENT ON COLUMN t_post_base_info.description IS '简介';
COMMENT ON COLUMN t_post_base_info.tags IS '标签';
COMMENT ON COLUMN t_post_base_info.view_num IS '访问数';
COMMENT ON COLUMN t_post_base_info.agree_num IS '点赞数';
COMMENT ON COLUMN t_post_base_info.favorite_num IS '收藏数';
COMMENT ON COLUMN t_post_base_info.repeat_num IS '转发数';
COMMENT ON COLUMN t_post_base_info.discuss_num IS '评论数';
COMMENT ON COLUMN t_post_base_info.original_type IS '创作类型，1：原创，2：转载';
COMMENT ON COLUMN t_post_base_info.reproduction_url IS '转载链接';
COMMENT ON COLUMN t_post_base_info.status IS '状态，1：公开，2：私有，3：删除';
COMMENT ON COLUMN t_post_base_info.create_by IS '创建人';
COMMENT ON COLUMN t_post_base_info.create_date IS '创建时间';
COMMENT ON COLUMN t_post_base_info.update_by IS '修改人';
COMMENT ON COLUMN t_post_base_info.update_date IS '修改时间';

-- 文章详情表
CREATE TABLE t_post_content_info
(
  post_id integer NOT NULL, -- 文章ID
  post_content character varying, -- 文章内容
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_post_content_info_pk PRIMARY KEY (post_id)
);
COMMENT ON TABLE t_post_content_info IS '文章基本信息表';
COMMENT ON COLUMN t_post_content_info.post_id IS '文章ID';
COMMENT ON COLUMN t_post_content_info.post_content IS '文章内容';
COMMENT ON COLUMN t_post_content_info.create_by IS '创建人';
COMMENT ON COLUMN t_post_content_info.create_date IS '创建时间';
COMMENT ON COLUMN t_post_content_info.update_by IS '修改人';
COMMENT ON COLUMN t_post_content_info.update_date IS '修改时间';

-- 友情链接表
CREATE TABLE t_related_links
(
  related_links_id character varying(10) NOT NULL, -- 主键
  user_id integer NOT NULL, -- 用户ID
  link_title character varying(50), -- 友链标题
  link_url character varying(200), -- 友链URL
  order_num double precision, -- 顺序
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_related_links_pk PRIMARY KEY (related_links_id )
);
COMMENT ON TABLE t_related_links IS '友情链接表';
COMMENT ON COLUMN t_related_links.related_links_id IS '主键';
COMMENT ON COLUMN t_related_links.user_id IS '用户ID';
COMMENT ON COLUMN t_related_links.link_title IS '友链标题';
COMMENT ON COLUMN t_related_links.link_url IS '友链URL';
COMMENT ON COLUMN t_related_links.order_num IS '顺序';
COMMENT ON COLUMN t_related_links.create_by IS '创建人';
COMMENT ON COLUMN t_related_links.create_date IS '创建时间';
COMMENT ON COLUMN t_related_links.update_by IS '修改人';
COMMENT ON COLUMN t_related_links.update_date IS '修改时间';

-- 用户喜爱（收藏）的文章表
CREATE TABLE t_post_favorite
(
  post_favorite_id character varying(10) NOT NULL, -- 主键
  user_id integer NOT NULL, -- 用户ID
  post_id integer NOT NULL, -- 文章ID
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_post_favorite_pk PRIMARY KEY (post_favorite_id)
);
COMMENT ON TABLE t_post_favorite IS '用户喜爱（收藏）的文章表';
COMMENT ON COLUMN t_post_favorite.post_favorite_id IS '主键';
COMMENT ON COLUMN t_post_favorite.user_id IS '用户ID';
COMMENT ON COLUMN t_post_favorite.post_id IS '文章ID';
COMMENT ON COLUMN t_post_favorite.create_by IS '创建人';
COMMENT ON COLUMN t_post_favorite.create_date IS '创建时间';
COMMENT ON COLUMN t_post_favorite.update_by IS '修改人';
COMMENT ON COLUMN t_post_favorite.update_date IS '修改时间';

-- 用户点赞的文章
CREATE TABLE t_post_agree
(
  post_agree_id character varying(10) NOT NULL, -- 主键
  user_id integer NOT NULL, -- 用户ID
  post_id integer NOT NULL, -- 文章ID
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_post_agree_pk PRIMARY KEY (post_agree_id)
);
COMMENT ON TABLE t_post_agree IS '用户点赞的文章';
COMMENT ON COLUMN t_post_agree.post_agree_id IS '主键';
COMMENT ON COLUMN t_post_agree.user_id IS '用户ID';
COMMENT ON COLUMN t_post_agree.post_id IS '文章ID';
COMMENT ON COLUMN t_post_agree.create_by IS '创建人';
COMMENT ON COLUMN t_post_agree.create_date IS '创建时间';
COMMENT ON COLUMN t_post_agree.update_by IS '修改人';
COMMENT ON COLUMN t_post_agree.update_date IS '修改时间';

-- 评论信息表
CREATE TABLE t_post_discuss
(
  discuss_id character varying(10) NOT NULL, -- 评论ID（主键）
  parent_discuss_id character varying(10), -- 父评论ID
  user_id integer NOT NULL, -- 评论用户ID
  post_id integer NOT NULL, -- 评论文章ID
  discuss_content character varying, -- 评论内容
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_post_discuss_pk PRIMARY KEY (discuss_id)
);
COMMENT ON TABLE t_post_discuss IS '评论信息表';
COMMENT ON COLUMN t_post_discuss.discuss_id IS '评论ID（主键）';
COMMENT ON COLUMN t_post_discuss.parent_discuss_id IS '父评论ID';
COMMENT ON COLUMN t_post_discuss.user_id IS '评论用户ID';
COMMENT ON COLUMN t_post_discuss.post_id IS '评论文章ID';
COMMENT ON COLUMN t_post_discuss.discuss_content IS '评论内容';
COMMENT ON COLUMN t_post_discuss.create_by IS '创建人';
COMMENT ON COLUMN t_post_discuss.create_date IS '创建时间';
COMMENT ON COLUMN t_post_discuss.update_by IS '修改人';
COMMENT ON COLUMN t_post_discuss.update_date IS '修改时间';


-- 广告信息表
CREATE TABLE t_ads_info
(
  ads_id character varying(10) NOT NULL, -- 广告ID（主键）
  img_url character varying(100), -- 图片URL
  link_url character varying(100), -- 链接URL
  show_begin timestamp without time zone, -- 展示开始时间
  show_end timestamp without time zone, -- 展示结束时间
  exclusive_flag character varying(1), -- 是否独占广告
  create_by integer, -- 创建人
  create_date timestamp without time zone NOT NULL DEFAULT now(), -- 创建时间
  update_by integer, -- 修改人
  update_date timestamp without time zone, -- 修改时间
  CONSTRAINT t_ads_info_pk PRIMARY KEY (ads_id)
);
COMMENT ON TABLE t_ads_info IS '广告信息表';
COMMENT ON COLUMN t_ads_info.ads_id IS '广告ID（主键）';
COMMENT ON COLUMN t_ads_info.img_url IS '图片URL';
COMMENT ON COLUMN t_ads_info.link_url IS '链接URL';
COMMENT ON COLUMN t_ads_info.show_begin IS '展示开始时间';
COMMENT ON COLUMN t_ads_info.show_end IS '展示结束时间';
COMMENT ON COLUMN t_ads_info.exclusive_flag IS '是否独占广告';
COMMENT ON COLUMN t_ads_info.create_by IS '创建人';
COMMENT ON COLUMN t_ads_info.create_date IS '创建时间';
COMMENT ON COLUMN t_ads_info.update_by IS '修改人';
COMMENT ON COLUMN t_ads_info.update_date IS '修改时间';