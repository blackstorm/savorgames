<template>
  <nav id="navbar" class="nav">
    <div class="container">
      <!--       <div class="logobox">  
      <a v-link="{ path: '/'}" title="这是一只傻狗">
        <img src="http://7xwe6g.com1.z0.glb.clouddn.com/logo.png" height="55"></a>
    </div>
    -->
    <div class="logo">
      <span>SavorGames</span>
    </div>
    <div class="navigation">
      <ul>
        <li>
          <a v-link="{ path: '/'}">首页</a>
        </li>
        <li>
          <a v-link="{ path: '/node'}">板块</a>
        </li>
        <li>
          <a v-link="{ path: '/tags'}">标签</a>
        </li>
        <li>
          <a href="">文章</a>
        </li>
        <li>
          <a v-link="{ path: '/games'}">Games</a>
        </li>
      </ul>
    </div>
    <div class="search">
      <input type="search" class="form-input input-inline" placeholder="Search" />  
    </div>

    <div class="action">
      <div class="use" v-if="userStore == null">
        <a v-link="{ path: '/signin', exact: true }" class="btn" style="border:none;">登录</a>
        <a v-link="{ path: '/signup', exact: true }" class=" btn btn-primary">注册</a>
      </div>
      <div class="use user" v-else>
        <span class="username">{{userStore.username}}</span>
        <figure class="avatar">
          <img :src="'//' +userStore.avatar"></figure>
        <ul class="items">
          <li>
            <a v-link="{ path: '/setting' }"> <i class="fa fa-cogs"></i>
              账户设置
            </a>
          </li>
          <li>
            <a href="">
              <i class="fa fa-plus"></i>
              反馈意见
            </a>
          </li>
          <li>
            <a v-link="{ path: '/topic/release', exact: true }" >
              <i class="fa fa-send-o"></i>
              发布帖子
            </a>
          </li>
          <li>
            <a href="javascript:void(0);" @click.prevent="logout()">
              <i class="fa fa-sign-out"></i>
              退出登录
            </a>
          </li>
          <li>
            <a v-link="{ path: '/management' }"><i class="fa fa-dashboard"></i>后台管理</a>
          </li>
        </ul>
      </div>
      <ul class="faqlink">
        <li>
          <a href="">FAQ</a>
        </li>
      </ul>
    </div>
  </div>
  </nav>
</template>
<script>
import { getUserInfoAction, logoutAction } from '../vuex/actions';

export default {
  vuex: {
    getters: {
      userStore: ({Auth}) => Auth.user
    },
    actions: {
      getUserInfoAction,
      logoutAction
    }
  },
  methods: {
    logout() {
      this.logoutAction();
    }
  }
};
</script>
<style>
.nav .search .form-input {
  width: 100%;
  border: none;
  background-color: #f2f2f2;
}
</style>