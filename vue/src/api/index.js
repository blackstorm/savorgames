import {
  AuthResource,
  UserResource,
  AvatarResource,
  NodeResource,
  TagResource,
  TopicResource,
  TopicLastResource,
  GamePlatformResource,
  GameStyleResource,
  TopicTabResource,
  ReplyRankResource,
  TopicRankResource,
  AdminPostResource,
  AdminPost
} from './resources';

export default {
  saveUser: function(data) {
    return AuthResource.save({do: 'register'}, data);
  },
  getCaptchaId: function() {
    return AuthResource.get({do: 'register'});
  },
  login: function(user) {
    return AuthResource.save({do: 'login'}, user);
  },
  getUser: function() {
    return UserResource.get({do: ''});
  },
  uploadAvatarCallBack: function(data) {
    return AvatarResource.save({do: 'upload'}, data);
  },
  saveUserNickName: function(data) {
    return UserResource.save({do: 'nickname'}, data);
  },
  getNodes: function() {
    return NodeResource.get({do: ''});
  },
  getTags: function() {
    return TagResource.get({do: ''});
  },
  saveTopic: function(data) {
    return TopicResource.save({do: ''}, data);
  },
  getLastTopicsByType: function(type) {
    return TopicLastResource.get({do: type});
  },
  getGamePlatforms: function() {
    return GamePlatformResource.get({do: ''});
  },
  getGameStyles: function() {
    return GameStyleResource.get({do: ''});
  },
  getTopicsByTab: function(key) {
    return TopicTabResource.get({tab: key});
  },
  getTopicById: function(id) {
    return TopicResource.get({do: id});
  },
  saveTopicReply: function(reply) {
    return TopicResource.save({do: 'reply'}, reply);
  },
  upReplyRank: function(id) {
    return ReplyRankResource.get({do: id});
  },
  upTopicRank: function(id) {
    return TopicRankResource.get({do: id});
  },
  getAdminPostByPage: function(page) {
    return AdminPostResource.get({page: page});
  },
  delAdminPostById: function(tid) {
    return AdminPost.delete({do: tid});
  }
};
