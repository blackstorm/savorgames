<template>
  <div class="comments" v-bind:class="{ 'cooments-padding': edit.show}">
    <div class="post-stream-item" v-for="r in comments" v-if="comments.length > 0 ? true:false" track-by="id">
      <article class="post">
        <header class="post-header">
          <ul>
            <li class="item-user">
              <div class="post-user">
                <h3>
                  <a href="">
                    <img class="post-user-avatar" :src="'//' + r.memberAvatar">  
                    <span class="poster" data-balloon="楼主" data-balloon-pos="top" v-if="r.memberId == posterId"><i class="fa fa-bookmark" aria-hidden="true"></i></span>
                    <span class="username">{{r.memberNickname}}</span>
                  </a>
                </h3>
                <a class="reply-num" :name="'reply-' + ( $index + 1)">#{{$index + 1}}</a>
              </div>
            </li>
            <li class="item-meta">
              <div class="post-meta">
                <time data-balloon-pos="top" data-balloon="2 天前">2016年8月8日星期一晚上9点14分</time>
              </div>
            </li>
            <li class="item-icon" v-if="user !== null">
              <a data-balloon-pos="top" data-balloon="+1" @click.prevent="rankOnclick(r.id)">
                <i class="fa fa-thumbs-o-up" aria-hidden="true"></i> {{r.rank}}
              </a>
            </li>
          </ul>
        </header>
        <div class="post-body">
          {{{r.content}}}
        </div>
        <footer class="post-footer">
          <div class="columns">
            <div class="column col-9">
              <div class="control">
                <ul>
                  <li class="link"><a href="JavaScript:;" v-if="user == null ? false : true">删除</a></li>
                </ul>
              </div>
            </div>
            <div class="column col-3">
              <div class="post-actions">
                <ul>
                  <li class="icon">
                    <a href="" @click.prevent="replyOncilick(r, $index+1)">回复</a>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </footer>
      </article>
    </div>
  </div>
</template>
<script>
import { commentsRankUpAction } from '../../vuex/actions';
import {swalLoginContent} from '../../utils/swal.js';
import swal from 'sweetalert'; // 更美观的alert
export default {
  props: ['edit'],
  methods: {
    replyOncilick(r, num) {
      var fnc = function() {
        swal.close();
        this.$router.go({name: 'signin'});
      }.bind(this);
      if (this.user === null) {
        swal(swalLoginContent, fnc);
        return;
      }
      if (r.memberId === this.user.uid) {
        swal('哦，朋友', '您不能回复您自己的评论', 'error');
        return;
      }
      console.log(num, this.comments.length);
      if (num === this.comments.length) { // 证明是最后一条评论，不需要悬浮回复框
        this.edit = {re: r, num: num};
        return;
      }
      this.edit = {re: r, num: num, show: true};
    },
    rankOnclick(id) { // rank + 1
      this.commentsRankUpAction(id);
    }
  },
  vuex: {
    getters: {
      comments: ({Topic}) => Topic.replys,
      user: ({Auth}) => Auth.user,
      posterId: ({Topic}) => Topic.topic.memberId
    },
    actions: {
      commentsRankUpAction
    }
  },
  ready() {
    // console.log(this.comments);
  }
};
</script>
<style>
.cooments-padding {
  padding-bottom: 120px;
}
.post-header .item-icon a{
  color:#bbb;
}
.post-header .item-icon a:hover {
  color:#1abc9c;
  cursor: pointer;
}
.comments .post-stream-item{
  border-bottom: 1px solid #eee;
}
.post-footer > ul, .post-actions > ul, .post-footer .control > ul{
  list-style-type: none;
  padding: 0px;
  margin: 0px;
}
.post-footer .control > ul li {
  margin-top: 0;
  display: inline-block;
  margin-right: 12px;
}
.post-footer .control > ul a {
  padding: 5px 8px;
  color:#999;
  font-size: 14px;
  border-radius: 4px;
}
.post-footer .control > ul a:hover {
  color: #fff;
  background-color: #999;
  font-size: 15px;
}
.post-footer {
  height:30px;
}
.post-footer .post-actions{
  display: none;
}
.post-stream-item:hover .post-actions {
  display: block;
}
.post-actions > ul > li {
  margin: 0px;
  display: inline-block;
  vertical-align: top;
  float: right;
  margin-left: 8px;
}
.post-actions > ul > li > a {
  color:#8FB299;
}
.post-actions > ul > li > a:hover {
  color:#1abc9c;
}
.poster {
  color:#1abc9c;
}
.reply-num {
  color:#666;
  font-size: 13px;
}
</style>