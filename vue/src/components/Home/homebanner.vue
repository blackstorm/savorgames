<template>
    <section class="home-section" v-show:="homebanner.show">
      <div class="logo" data-balloon="Some people say that I am a stupid dog, but I do not believe!" data-balloon-pos="right">
        <img src="http://7xwe6g.com1.z0.glb.clouddn.com/logo.png" height="66px;" />
      </div>
      <h1>游戏味道</h1>
      <h3>通过用户上传的游戏视频、游戏图片、基于社区的游戏评论等，您可以直接快速的了解到该游戏的信息。</h3>
      <a class="close" data-balloon="关闭" data-balloon-pos="left" @click.prevent="homebanner.show = false"><i class="fa fa-times-circle"></i></a>
    </section>
</template>
<script>
import { setHomeBannerCookie, getHomeBannerCookie } from '../../utils/cookie';

export default {
  data() {
    return {
      homebanner: {
        show: true
      }
    };
  },
  watch: {
    'homebanner.show': (val) => {
      if (val === false) {
        document.querySelector('#navbar').classList.remove('navbar-backgorund'); // 这边必须使用监听不能使用方法，不知道为什么。
        setHomeBannerCookie(false);
      }
    }
  },
  methods: {
    addNavbarBackground() {
      document.querySelector('#navbar').classList.add('navbar-backgorund');
    }
  },
  ready() {
    var homeBannerCookie = getHomeBannerCookie();
    if (homeBannerCookie === undefined) { // 如果 cookie 不存在
      this.addNavbarBackground();
      setHomeBannerCookie(true);
      return;
    }
    if (homeBannerCookie === true) {
      this.addNavbarBackground();
      return;
    }
    if (homeBannerCookie === false) { // 如果cookie设置为不显示banner
      this.homebanner.show = false;
    }
  },
  destroyed() {
    document.querySelector('#navbar').classList.remove('navbar-backgorund');
  }
};
</script>
<style>
.home-section {
  position:relative;
  margin-top: -20px;
  padding: 40px 40px 60px 40px;
  background: white;
  text-align: center;
  background-color: #f8f8f8;
}
.navbar-backgorund {
  background-color: #f8f8f8;
}
.home-section .logo{
  height: 66px;
  width: 66px;
  margin: auto;
  border-radius: 50%;
  background-color: #fff;
}
.home-section h1{
  font-size: 4rem;
}
.home-section h3{
  font-size:2rem;
}

.home-section:hover .close {
  visibility:visible;
}

.home-section .close{
  position: absolute;
  right: 8px;
  top: 6px;
  font-size: 18px;
  color:#999;
  visibility:hidden;
}
.home-section .close:hover{
  cursor: pointer;
}
</style>