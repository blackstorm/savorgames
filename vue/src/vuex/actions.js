import api from '../api';
import * as types from './types';
import {API_ROOT} from '../config';
import { saveCookie, removeCookie } from '../utils/authService';

const SUC = 'success';

// 显示TOAST
export const showMsg = ({dispatch}, content, type = 'error') => {
  dispatch(types.SHOW_MSG, {content: content, type: type});
};

// 隐藏TOAST
export const hideMsg = ({dispatch}) => {
  dispatch(types.HIDE_MSG);
};

// 注册
export const registAction = (store, userForm) => {
  store.dispatch(types.REGISTER_BUTTON_TRUE);
  api.saveUser(userForm).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      getCaptchaIdAction(store);
      store.dispatch(types.REGISTER_BUTTON_FALSE);
      return showMsg(store, response.data.message || '注册失败');
    }
    showMsg(store, '注册成功，请登录', 'success');
    store.router.go({path: '/signin'});
  }, response => {
    getCaptchaIdAction(store);
    store.dispatch(types.REGISTER_BUTTON_FALSE);
    return showMsg(store, response.data.message || '注册失败');
  });
};

// 登录
export const loginAction = (store, userinfo) => {
  api.login(userinfo).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      return showMsg(store, response.data.message || '未知错误');
    }
    const token = response.data.token;
    // 保存token到cookie中
    saveCookie('token', token);
    // 保存用户信息
    store.dispatch(types.AUTH_LOGIN_SUCCESS, {token: token});
    getUserInfoAction(store);
    // 提示
    showMsg(store, '登录成功', 'success');
    // 跳转首页
    store.router.go({path: '/'});
  }, response => {
    return showMsg(store, response.data.message || '登录失败');
  });
};

// 退出登陆
export const logoutAction = (store) => {
  removeCookie('token');
  // 置空用户信息
  store.dispatch(types.LOGOUT_USER);
  // 强制跳转防止安全隐患
  store.router.go({path: '/'});
};

//  获取用户信息
export const getUserInfoAction = ({ dispatch }) => {
  api.getUser().then(response => {
    if (!response.ok) {
      return dispatch(types.USERINFO_FAILURE);
    }
    dispatch(types.USERINFO_SUCCESS, { user: response.data.result.user, status: response.data.result.status });
  }, response => {
    dispatch(types.USERINFO_FAILURE);
  });
};

// 获取验证码
export const getCaptchaIdAction = ({ dispatch }) => {
  api.getCaptchaId().then(response => {
    if (!response.ok) {
      window.alert('请求失败');
      return;
    }
    var captchaurl = API_ROOT + 'auth/register/captcha?id=' + response.data.message;
    dispatch(types.GET_CAPTCHA_ID, response.data.message, captchaurl);
  }, response => {
    window.alert('请求失败');
  });
};

// 七牛上传成功回调函数
export const uploadAvatarCallBackAction = (store, info) =>{
  api.uploadAvatarCallBack(info).then(response => {
    if (!response.ok) {
      showMsg(store, '上传失败，请重新上传');
      return;
    };
    showMsg(store, '上传成功，请刷新缓存查看图片是否更新');
    store.dispatch(types.AUTH_UPLOAD_AVATAR_SUCCESS, {avatar: response.data.result});
  }, response => {
    showMsg(store, '上传失败，请重试');
  });
};

export const uploadNickNameAction = (store, nickname) => {
  api.saveUserNickName(nickname).then(response => {
    if (!response.ok) {
      showMsg(store, '更新昵称失败:' + response.data.message);
      return;
    }
    showMsg(store, '更新昵称成功');
    getUserInfoAction(store);
  }, response => {
    showMsg(store, '更新昵称失败');
  });
};

export const getNodesAction = (store) => {
  api.getNodes().then(response => {
    if (!response.ok) {
      showMsg(store, '拉取节点异常:' + response.data.message);
      return;
    }
    store.dispatch(types.GET_NODES_SUCCESS, {nodes: response.data.nodes});
  }, response => {
    showMsg(store, '获取节点信息失败，请刷新重试');
  });
};

// 获取标签
export const getTagsActions = (store) => {
  api.getTags().then(response => {
    if (!response.ok) {
      showMsg(store, '拉取标签异常:' + response.data.message);
      return;
    }
    store.dispatch(types.GET_TAGS_SUCCESS, {tags: response.data.result});
  }, response => {
    showMsg(store, '获取TAG失败，请重试');
  });
};

// 发布帖子
export const releaseTopicActions = (store, topic) => {
  store.dispatch(types.RELEASE_TOPIC_BUTTON_SUCCESS);
  api.saveTopic(topic).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, '发布失败:' + response.data.message);
      store.dispatch(types.RELEASE_TOPIC_BUTTON_FIL);
      return;
    }
    showMsg(store, '发布成功', 'success');
    // 下面准备跳转。。。
    store.dispatch(types.RELEASE_TOPIC_BUTTON_FIL); // 重置button
  }, response => {
    store.dispatch(types.RELEASE_TOPIC_BUTTON_FIL);
    showMsg(store, '发布失败，请尝试重新发布');
  });
};

// 获取最后主题信息
export const getTypeLastTopicsAction = (store, type) => {
  api.getLastTopicsByType(type).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, '拉取最后主题失败:' + response.data.message);
      return;
    }
    store.dispatch(types.GET_NODES_LAST_TOPICS_SUCCESS, {res: response.data.result});
  }, response=> {
    showMsg(store, '拉取失败，请重试');
  });
};

// 拉取游戏平台
export const getGamePlatformsAction = (store) => {
  api.getGamePlatforms().then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, '拉取游戏平台失败:' + response.data.message);
      return;
    }
    store.dispatch(types.GET_PLATFORM_SUCCESS, {platforms: response.data.result});
  }, response => {
    showMsg(store, '拉取游戏平台失败');
  });
};

export const getGameStylesAction = (store) => {
  api.getGameStyles().then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, 'Pull game style fil:' + response.data.message);
      return;
    }
    store.dispatch(types.GET_GAMESTYLE_SUCCESS, {styles: response.data.result});
  }, response=> {
    showMsg(store, 'Pull game style fil');
  });
};

// 按照tab获取首页帖子
export const getTabTopicsAction = (store, tab) => {
  store.dispatch(types.HOME_MENU_TOPIC_LOADING_TRUE);
  api.getTopicsByTab(tab).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, '没有获取到贴子:' + response.data.message);
    }
    store.dispatch(types.GET_RANK_TOPICS_SUCCESS, {topics: response.data.result});
    store.dispatch(types.HOME_MENU_TOPIC_LOADING_FALSE);
  }, response => {
    showMsg(store, '哦，没有获取到首页帖子');
  });
};

// 获取帖子
export const getTopicAction = (store, id) => {
  api.getTopicById(id).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, '没有获取到贴子:' + response.data.message);
      return;
    }
    // DOTO
    var replys = response.data.replys;
    // console.log(replys === undefined);
    if (replys === undefined || replys === null) replys = [];
    store.dispatch(types.GET_TOPIC_SUCCESS, {topic: response.data.topic, replys: replys, tags: response.data.tags});
  }, response => {
    showMsg(store, '没有获取到贴子');
  });
};

export const saveTopicReplyAction = (store, reply) => {
  api.saveTopicReply(reply).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, '发布失败:' + response.data.message);
      return;
    }
    store.dispatch(types.SAVE_TOPIC_REPLY_SUCCESS, {replys: response.data.result});
    showMsg(store, '发布成功！', 'success');
  }, response => {
    showMsg(store, '发布失败');
  });
};

export const commentsRankUpAction = (store, replyid) => {
  api.upReplyRank(replyid).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, '失败' + response.data.message);
      return;
    }
    store.dispatch(types.UP_TOPIC_REPLY_SUCCESS, {id: replyid});
  }, response => {
    showMsg(store, response.data.message);
  });
};

export const topicRankUpAction = (store, topicId) => {
  api.upTopicRank(topicId).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, '失败' + response.data.message);
      return;
    }
    store.dispatch(types.UP_TOPIC_RANK);
  }, response => {
    showMsg(store, response.data.message);
  });
};

export const getAdminPostsActions = (store, page) => {
  api.getAdminPostByPage(page).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, response.data.message);
      return;
    }
    store.dispatch(types.GET_ADMIN_POSTS_SUCCESS, {posts: response.data.result});
  }, response => {
    showMsg(store, response.data.message);
  });
};

// 删除帖子
export const adminDelTopicActions = (store, tid) => {
  api.delAdminPostById(tid).then(response => {
    if (!response.ok || response.data.status !== SUC) {
      showMsg(store, response.data.message);
      return;
    }
    store.dispatch(types.ADMIN_DEL_TOPIC_SUCCESS, {tid: tid});
  }, response => {
    showMsg(store, response.data.message);
  });
};
