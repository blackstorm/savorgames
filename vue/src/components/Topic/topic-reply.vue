<template>
  <div class="post-reply-box" v-bind:class="{'post-reply-fly': edit.show}">
    <div class="top-bar" v-if="edit.re !== null">
      <ul>
        <li class="re-member">
          <i class="fa fa-mail-forward" aria-hidden="true"></i>
          <a :href="'#reply-' + edit.num"><img :src=" '//' + edit.re.memberAvatar"> {{edit.re.memberNickname}} {{'#' + edit.num}}</a>
        </li>
        <li style="float:right;margin-right:4px;">
          <a href="JavaScript:;" @click.prevent="clearReply()">X</a>
        </li>
      </ul>
    </div>
    <div class="member" v-if="user === null ? true : false">
      <img class="hvr-wobble-vertical" src="http://7xwe6g.com1.z0.glb.clouddn.com/reply-default-avatar.jpg"></div>
    <div class="member" v-else>
      <img class="hvr-wobble-vertical" :src=" '//' + user.avatar"></div>
    <div class="editable" @click="editorOnclick()"></div>
    <div class="post-reply-controller" v-if="user === null ? false : true">
      <ul>
        <li class="submit">
          <button class="btn btn-primary" :class="{loading : buttonStore}" @click.prevent="replyOnSubmit()">发布</button>
        </li>
        <li v-show="edit.re !== null">
          <a href="JavaScript:;" @click.prevent="clearReply()">取消</a>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import MediumEditor from 'medium-editor';
import '../../assets/style/medium-editor.css'; // medium-editor样式
import '../../assets/style/flat.min.css'; // medium-editor样式
import '../../assets/style/hover.css'; // 鼠标hover样式
import swal from 'sweetalert'; // 更美观的alert
import {swalLoginContent} from '../../utils/swal.js';
import '../../assets/style/sweetalert.css'; // aler样式
import {saveTopicReplyAction} from '../../vuex/actions';

export default {
  props: ['tid', 'edit'],
  vuex: {
    getters: {
      buttonStore: ({ReplyRelease}) => ReplyRelease.submitButton,
      user: ({Auth}) => Auth.user
    },
    actions: {
      saveTopicReplyAction
    }
  },
  data() {
    return {
      editor: null
    };
  },
  methods: {
    clearReply() {
      this.edit = {re: null, num: null, show: false};
    },
    editorOnclick() {
      var fnc = function() {
        swal.close();
        this.$router.go({name: 'signin'});
      }.bind(this);
      if (this.user === null) {
        swal(swalLoginContent, fnc);
        return;
      }
    },
    replyOnSubmit() {
      var content = this.editor.getContent();
      if (content === null) {
        return;
      }
      this.saveTopicReplyAction({topicId: this.tid, parentId: 0, content: content});
    }
  },
  ready() {
    var edit = document.querySelectorAll('.editable');
    var options = {
      toolbar: {
        buttons: ['bold', 'italic', 'underline', 'anchor', 'quote']
      }
    };
    this.editor = new MediumEditor(edit, options);
  }
};
</script>
<style>
.post-reply-box {
  width: 1200px;
  margin:auto;
 /* padding-left: 1rem; 突然一个原因造成偏移*/
  padding-right: 2rem;
  margin-top: 20px;
}

.post-reply-fly {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1200;
  background-color: #f2f2f2;
  padding-bottom: 10px;
  border-radius: 8px;
  min-height: 200px;
  transition: height 0.4s ease;
  padding-left: 1rem;
  padding-right: 1rem;
}

.post-reply-fly .editable {
  border:2px dashed #1abc9c;
}

.editable {
  margin-left: 85px;
  padding: 20px;
  border:2px dashed #9a9a9a;
  border-radius: 8px;
  outline: none;
}

.post-reply-box > .member{
  max-width: 64px;
  float: left;
}
.post-reply-fly .top-bar {
  padding-top: 10px;
}
.post-reply-box .top-bar {
  margin-left:85px;
  padding-bottom:5px;
}

.post-reply-box .top-bar > ul{
  margin:0;
}

.post-reply-box .top-bar > ul >li {
  margin-top: 0;
  display: inline-block;
  margin-right: 10px;
}

.post-reply-box .top-bar > ul > li > a{
  display: inline-block;
}

.post-reply-box .top-bar .re-member img{
  height:20px;
  border-radius:50%;
}

.post-reply-box .top-bar .re-member {
  color:#888;
}

.post-reply-box > .member img {
  max-width: 64px;
  border-radius: 8px;
}
.post-reply-controller {
  margin-left: 85px;
}
.post-reply-controller ul ,.post-reply-controller li {
  margin:0;
  padding:0;
}
.post-reply-controller{
  margin-top: 15px;
}
.post-reply-controller > ul > li {
    display: inline;
    margin-right: 15px;
}
.post-reply-controller .submit button {
  border-radius: 18px;
}
</style>