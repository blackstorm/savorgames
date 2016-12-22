<template>
<div class="loginpage">
<div class="container index-content">
  <div class="login-box">
    <h1>有你的世界更精彩</h1>
    <validator name="loginValidation">
    <form @submit.prevent="login()">
      <div class="form-group">
        <input class="form-input" v-model="form.username" type="text" v-validate:username="{ required: true }" placeholder="邮箱或用户名" />
      </div>
      <div class="form-group">
        <input class="form-input" type="password" v-model="form.password" v-validate:password="{ required: true, minlength: 6, maxlength: 18 }" placeholder="密码" />
      </div>
      <div class="form-group">
        <a>忘记密码？</a>
      </div>
      <div class="form-group">
        <button class="btn btn-primary btn-block" type="submit" :disabled="$loginValidation.invalid">登录</button>
      </div>
    </form>
    </validator>
  </div>
</div>
</div>
</template>
<script>
import {loginAction} from '../../vuex/actions';

export default {
  vuex: {
    actions: {
      loginAction
    }
  },
  data() {
    return {
      form: {
        username: null,
        password: null
      }
    };
  },
  methods: {
    login() {
      this.loginAction(this.form);
    }
  },
  ready() {
    var rootel = document.querySelector('#root');
    rootel.classList.add('loginrootbackground');
    var div = document.createElement('div');
    div.id = 'login-background-modal';
    rootel.appendChild(div);
  },
  destroyed() {
    var root = document.querySelector('#root');
    root.classList.remove('loginrootbackground');
    root.removeChild(document.querySelector('#login-background-modal'));
  }
};
</script>
<style>
.loginrootbackground {
  background: url(http://cdn.steamstatic.com.8686c.com/steam/apps/271590/ss_43e9c59d968e7f99f1eef065af85b3e542100366.jpg?t=1470324156);
  background-repeat: no-repeat, no-repeat, repeat;
  background-size: cover;
  z-index: 0;
}

#login-background-modal {
  position: absolute;
  top:0;
  height: 100%;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.3);
  z-index: -1;
}

.loginrootbackground .footer-style {
  background-color:rgba(255,255,255,0);
}
.loginrootbackground .navigation a{
  color:#e5e9ea;
}

.loginrootbackground .search > input[type='search'] {
  height: 30px;
  background-color:rgba(255,255,255,0);
}

.loginrootbackground .search > input[type='search']:focus{
  background-color: rgba(255,255,255,.3);
}

.loginpage{
  height: 100%;
  width: 100%;
/*  min-height: 918px;*/
}
.login-box{
  width: 400px;
  margin: auto;
  padding-top:180px;
}
.login-box .form-input,.loginrootbackground .search > input[type='search']{
  -webkit-transition: all .4s;
  -moz-transition: all .4s;
  -ms-transition: all .4s;
  -o-transition: all .4s;
  transition: all .4s;
}
.login-box .form-input {
  background-color: rgba(255,255,255,.3);
  border: 1px solid #fff;
  text-align: center;
}

.login-box .btn[type='submit'] {
  border-radius: 24px;
}

.login-box .form-input:focus{
  color:#333;
  background-color: #fff;

}
.login-box form{
  border-radius: 2px;
  padding:30px;
}
</style>