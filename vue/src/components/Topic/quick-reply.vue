<template>
  <div class="quick-reply">
    <div class="top-bar">
      <ul>
        <li class="re">
          <img :src=" '//' + reuser.memberAvatar">
          <span>memberNickname</span>
          <span>#1</span>
        </li>
      </ul>
    </div>
    <div class="content">
      <div class="quick-editable" @click="editorOnclick()"></div>
    </div>
    <div class="footer">
      <ul>
        <li><button>发布</button></li>
        <li><button>取消</button></li>
      </ul>
    </div>
  </div>
</template>
<script>
import MediumEditor from 'medium-editor';

export default {
  props: ['re', 'show'],
  data() {
    return {
      edit: null
    };
  },
  vuex: {
    getters: {
      tid: ({Topic}) => Topic.topic.topicId
    }
  },
  ready() {
    var edit = document.querySelectorAll('.quick-editable');
    var options = {
      toolbar: {buttons: ['bold', 'italic', 'underline', 'anchor', 'quote']}
    };
    this.edit = new MediumEditor(edit, options);
  }
};
</script>
<style>
.quick-reply {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 100000000000;
  pointer-events: none;
  background-color: #333;
}

.quick-reply .top-bar,.quick-reply .content,.quick-reply .footer{
  width: 1200px;
  margin:auto;
}
.quick-reply .top-bar > ul{
  margin:0;
}
.quick-reply .top-bar > ul >li {
  margin-top: 0;
  display: inline-block;
}
.quick-editable {
  height: 336px;
}
</style>