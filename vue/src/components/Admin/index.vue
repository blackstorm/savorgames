<template>
  <div id="admin-layout">
    <div class="welcome" v-show="tab == null ? true : false">
      <h1>欢迎您，{{adminStatus}}：{{username}}</h1>
    </div>
    <div id="basic_apps" class="apps" v-show="tab == null ? true : false">
      <a href="JavaScript:;" @click.prevent="tab = 'post'"><i class="fa fa-file-text-o"></i>
        <span>帖子管理</span>
      </a>
      <a href="/service/app/choose_site?title=Web Editor&amp;path=/service/app/web_editor"> <i class="fa fa-edit"></i>
        <span>Web Editor</span>
      </a>
      <a href="/service/app/wechat">
        <i class="fa fa-wechat"></i>
        <span>微信</span>
      </a>
      <a href="/service/app/dropbox">
        <i class="fa fa-dropbox"></i>
        <span>Dropbox</span>
      </a>
      <a href="http://markeditor.farbox.com/">
        <i class="fa fa-download"></i>
        <span>MarkEditor</span>
      </a>
      <a href="/service/app/desktop_editor">
        <i class="fa fa-download"></i>
        <span>FarBox Editor</span>
      </a>
      <a href="/service/app/setup">
        <i class="fa fa-cog"></i>
        <span>账户设置</span>
      </a>
      <a href="/service/app/info">
        <i class="fa fa-info"></i>
        <span>账户信息</span>
      </a>
      <a href="/service/sync_logs">
        <i class="fa fa-refresh"></i>
        <span>同步记录</span>
      </a>
      <div class="clear"></div>
    </div>
    <div class="childs">
      <Posts v-if="tab == 'post'" :tab.sync="tab"></Posts>
    </div>
  </div>
</template>
<script>
import Posts from './childs/post.vue';

export default {
  vuex: {
    getters: {
      adminStatus: ({Auth}) => Auth.status,
      username: ({Auth}) => Auth.user.username
    }
  },
  data() {
    return {
      tab: null
    };
  },
  components: {
    Posts
  },
  ready() {
    // if (this.adminStatus === null) { 这里重定向有BUG
    //   this.$router.go('/');
    //   return;
    // }
  }
};
</script>
<style>
#admin-layout {
  margin: 50px 0 80px 0;
}
.apps {
    width: 100%;
    display: block;
}
.apps a {
  display: block;
  width: 33.33333%;
  border: none;
  padding: 0;
  float: left;
  text-align: center;
  margin: 2em 0 4em 0;
  height: 6em;
  text-decoration: none;
  color: #555;
  overflow: hidden;
}
.apps a i {
    display: block;
    color: #666;
    font-size: 2em;
    margin: 10px 0;
}
.welcome {
  width: 1200px;
  margin:auto;
  text-align: center;
  margin-bottom: 30px;
}
.welcome h1 {
  margin:0;
  padding: 0;
  font-size: 24px;
}
.apps a span {
    display: block;
    font-size: 1.2em;
    border: none;
    padding: 0;
}
</style>