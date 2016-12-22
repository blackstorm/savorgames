<template>
  <div id="post">
    <!--banner start-->  
    <div class="banner">
      <Registerbanner></Registerbanner>
    </div>
    <!--banner end-->
    <div class="post-title">
      <h1><a href="JavaScript:;" name="title">#</a>{{topicStore.topicTitle}}</h1>
    </div>
    <div class="post-node-tags">
      <ul>
        <li class="node">板块：<a href=""><span class="label label-primary">{{topicStore.nodeName}}</span></a></li>
        <li  class="tag" v-if="tagsStore.length > 0 ? true:false">
          标签：<a href="" v-for="t in tagsStore "><div class="chip-sm"><span class="chip-name">{{t.tagName}}</span></div></a>
        </li>
      </ul>
    </div>
    <!--post-stream start-->  
    <div class="post-stream">
      <div class="post-stream-item">
        <article class="post">
          <header class="post-header">
            <ul>
              <li class="item-user">
                <div class="post-user">
                  <h3>
                    <a href="">
                      <img class="post-user-avatar" :src=" '//' + topicStore.memberAvatar " />  
                      <span class="username">{{topicStore.memberNickname}}</span>
                    </a>
                  </h3>
                </div>
              </li>
              <li class="item-meta">
                <div class="post-meta">
                  <time :data-balloon="fornow(topicStore.topicDate)" data-balloon-pos="top">
                    {{created}}
                  </time>
                </div>
              </li>
            </ul>
          </header>
          <div class="post-body">{{{topicStore.topicContent}}}</div>
        </article>
      </div>
      <Streambar :sequence.sync="sequence" :user="userStore" :topic="topicStore"></Streambar>
      <Comments :edit.sync="replyeditor"></Comments>
      <Replyeditor :tid="topicStore.topicId" :edit.sync="replyeditor"></Replyeditor>
    </div>
    <!--post-stream end--> 
  </div>
</template>
<script>
import Registerbanner from '../banner/registerBanner.vue';
import Streambar from './topic-stream-bar.vue';
import Comments from './topic-comments.vue';
import Replyeditor from './topic-reply.vue';
import {getTopicAction} from '../../vuex/actions';
var moment = require('moment');

export default {
  data() {
    return {
      replyeditor: {
        re: null,
        num: null,
        show: false
      }
    };
  },
  computed: {
    created() {
      return moment(this.topicStore.topicDate).format('LLLL');
    }
  },
  methods: {
    fornow(time) {
      return moment(time).fromNow();
    }
  },
  vuex: {
    actions: {
      getTopicAction
    },
    getters: {
      userStore: ({Auth}) => Auth.user,
      topicStore: ({Topic}) => Topic.topic,
      tagsStore: ({Topic}) => Topic.tags
    }
  },
  components: {
    Registerbanner,
    Streambar,
    Comments,
    Replyeditor
  },
  route: {
    data({to: {params: {id}}}) {
      this.getTopicAction(id);
    }
  },
  ready() {
    moment.locale('zh-cn');
  }
};
</script>
<style>
#post {
  width: 100%;
}

#post > .banner {
  width: 1200px;
  margin:auto;
}

#post > .post-stream {
  margin-top: 10px;
  width: 1200px;
  padding-left:1rem;
  padding-right: 1rem;
  margin:auto;
}

.post-stream-item {
  /*border-bottom: 1px solid rgb(232, 236, 243);*/
}

#post > .post-stream > .post-stream-item{
  display: block;
}

.post-title, .post-node-tags{
  padding-left:1rem;
  padding-right: 1rem;
  width: 1200px;
  margin:auto;
}

.post-node-tags ul,.post-node-tags li {
  margin:0;
  padding: 0;
  display: inline;
}

.post-node-tags ul {
  margin-left:8px;
}

.post-node-tags li  {
  margin-right: 15px;
}

.post-node-tags .node {
  color:#666;
}

.post-node-tags .tag > a {
  margin-right: 10px;
}

.post-node-tags .tag .chip-name{
      margin-left:0;
}

.post-node-tags .node > a{
  background-color: #f2f2f2;
}

a.node {
  padding: 10px;
  background-color: #f2f2f2;
}

.post {
  padding: 20px;
  margin: -1px -20px;
  transition: .2s box-shadow, top .2s, opacity .2s;
  position: relative;
  top: 0;
  border-radius: 10px;
}

.post {
  padding-left: 105px;
}

.post-header {
  margin-bottom: 20px;
}

.post-header ul, .post-header li, .post-stream-action ul, .post-stream-action li{
  margin:0;
  padding: 0;
}

.post-header > ul > li {
  display: inline;
  margin-right: 10px;
}
.post-user {
  margin: 0;
  display: inline;
  font-weight: normal;
  position: relative;
}
.post-user .username {
  /*color:#BC451A;*/
}
.post-user h3, .post-user h3 a {
  color: #111;
  font-weight: bold;
  font-size: 14px;
}
.post-user h3 {
  display: inline;
  line-height: 1.3;
}
.post-user-avatar {
    margin-left: -85px;
    float: left;
    width: 64px;
    height: 64px;
    border-radius: 64px;
    font-size: 32px;
    line-height: 64px;
    display: inline-block;
    box-sizing: content-box;
    color: #fff;
    text-align: center;
    vertical-align: top;
    font-weight: normal;
}
.post-meta {
  display: inline;
  font-size: 14px !important;
  color:#8FB299;
  font-weight: bold;
}
.post-body {
    font-size: 16px;
    line-height: 2;
    letter-spacing: 1.5px;
    position: relative;
    overflow: auto;
    word-wrap: break-word;
}

.post-meta time{
  font-size: 13px;
  font-weight: normal;
}
</style>