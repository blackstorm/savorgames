<template>
  <section class="register-banner" v-show:="showBanner">
    <div class="banner-close" @click.prevent="hiddenRegisterBanner()">x</div>
    <div class="columns banner-content">
      <div class="column col-6">
        <div class="introduction">
          <h4 style="padding:0;margin:0;">快点朋友，我们要赶不上了</h4>
          <p>
            听我说伙计，我们要去的地方充满了奇妙，那儿有着爱不释手的游戏、还能认识许多志同道合的朋友、最让人拍案叫绝的是这个网站叫 SavorGames
          </p>
        </div>
      </div>
      <div class="column col-6">
        <div class="start">
          <a v-link="{ name: 'signup' }" class="register-btn">出发</a>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import {getRegisterBannerCookie, setRegisterBannerCookie} from '../../utils/cookie';

export default {
  vuex: {
    getters: {
      userTokenStore: ({Auth}) => Auth.token
    }
  },
  data() {
    return {
      showBanner: true
    };
  },
  methods: {
    hiddenRegisterBanner() {
      this.showBanner = false;
      setRegisterBannerCookie(false);
    }
  },
  created() {
    var value = getRegisterBannerCookie();
    if (this.userTokenStore !== null) {
      this.showBanner = false;
      return;
    }
    if (this.userTokenStore === null && value === null) { // 没有登录且是第一次访问
      setRegisterBannerCookie(true);
      return;
    }
    if (value === false) { // 已经关闭
      this.showBanner = value;
      return;
    }
  }
};
</script>
<style>
.register-banner {
  position: relative;
  width: 100%;
  height:225px;
  position: relative;
  background-color:#FFFFE5;
  background: url(http://7xwe6g.com1.z0.glb.clouddn.com/register.png);
  color:#63994C;
  border-radius: 2px;
}
.register-banner .banner-close {
  position: absolute;
  top: 8px;
  right: 8px;
  font-size: 11px;
  cursor: pointer;
  color: #999;
}
.banner-content{
  height: auto;
  padding: 40px;
}
.register-banner .column .introduction {
  width: 80%;
}
.register-banner .column .introduction p{
  margin-top: 17px;
  line-height: 28px;
}
.register-btn{
  position: relative;
  color: rgba(255,255,255,1);
  text-decoration: none;
  background-color: rgba(219,87,5,1);
  font-weight: 700;
  font-size: 3em;
  display: block;
  padding: 4px;
  -webkit-border-radius: 8px;
  -moz-border-radius: 8px;
  border-radius: 8px;
  -webkit-box-shadow: 0px 9px 0px rgba(219,31,5,1), 0px 9px 25px rgba(0,0,0,.7);
  -moz-box-shadow: 0px 9px 0px rgba(219,31,5,1), 0px 9px 25px rgba(0,0,0,.7);
  box-shadow: 0px 9px 0px rgba(219,31,5,1), 0px 9px 25px rgba(0,0,0,.7);
  width: 160px;
  text-align: center;
  -webkit-transition: all .1s ease;
  -moz-transition: all .1s ease;
  -ms-transition: all .1s ease;
  -o-transition: all .1s ease;
  transition: all .1s ease;
}

.register-btn:hover{
  color: rgba(255,255,255,1);
  cursor: pointer;
}

.register-btn:active {
    -webkit-box-shadow: 0px 3px 0px rgba(219,31,5,1), 0px 3px 6px rgba(0,0,0,.9);
    -moz-box-shadow: 0px 3px 0px rgba(219,31,5,1), 0px 3px 6px rgba(0,0,0,.9);
    box-shadow: 0px 3px 0px rgba(219,31,5,1), 0px 3px 6px rgba(0,0,0,.9);
    position: relative;
    top: 6px;
}
.register-banner .column .start {
  margin-left: 60%;
  width: 30%;
  padding-top: 28px;
}
</style>