<template>
<div class="container index-content">
    <div class="signupbox">
      <div class="columns">
          <div class="column col-12">
          <validator name="registerValidation">
            <form class="signup-form" @submit.prevent="regist($registerValidation)">
              <div class="form-group">
                <h4 class="suc">选择一个初始的头像</h4>
                <div class="select-avatar">
                  <span class="clickicon" style="float:left;" @click="changeDefaultAvatar('left')"><i class="fa fa-chevron-left"></i></span>
                  <img :src="defaultAvatr[avatarSubscript]" width="72">
                  <span class="clickicon" style="float:right;" @click="changeDefaultAvatar('right')"><i class="fa fa-chevron-right"></i></span>
                </div>
              </div>
              <div class="form-group">
                <h4 :class="[$registerValidation.username.invalid ? '': 'suc']">填写您的用户名</h4>
                <input class="form-input" type="text" v-model="form.username" v-validate:username="{ required: true, minlength: 3, maxlength: 12 }" placeholder="用户名3-12" />
              </div>
              <div class="form-group">
                <h4 :class="[$registerValidation.password.invalid ? '': 'suc']">填写一个好用的密码</h4>
                <input class="form-input" type="password" v-model="form.password" v-validate:password="{ required: true, minlength: 6, maxlength: 18 }" placeholder="密码6-18" />
              </div>
              <div class="form-group">
                <h4 :class="[$registerValidation.email.invalid ? '': 'suc']">输入常用的邮箱</h4>
                <input class="form-input" type="email" v-model="form.email" v-validate:email="{ required: true, email: true }" placeholder="email" />
              </div>
              <div class="form-group captcha">
                <h4 :class="[$registerValidation.captcha.invalid ? '': 'suc']">确保您不是机器人</h4>
                <img :src="captchaStore.url" @click.prevent="getCaptchaIdAction()">
                <input class="form-input" type="text" v-model="form.captcha" v-validate:captcha="{ required: true, minlength: 5, maxlength: 5 }" placeholder="输入上方验证码" />
                <input type="hidden" v-model="form.captchaid" :value="captchaStore.id" />
              </div>
              <div class="form-group">
                <button class="btn btn-primary btn-block" type="submit" :disabled="$registerValidation.invalid" v-bind:class="{'loading': loadingClass}">注册</button>
              </div>
            </form>
          </validator>
          </div>
      </div>
    </div>
</div>
</template>
<script>
import {registAction, getCaptchaIdAction} from '../../vuex/actions';
import {Qiniu_Http_Domain} from '../../api/resources';

export default {
  vuex: {
    getters: {
      captchaStore: ({Register}) => Register.captcha,
      loadingClass: ({Register}) => Register.submitButton
    },
    actions: {
      registAction,
      getCaptchaIdAction
    }
  },
  validators: {
    email: function(val) {
      // return /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(val);
      return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(val);
    }
  },
  data() {
    return {
      defaultAvatr: [
        Qiniu_Http_Domain + '/default-avatars-01.png',
        Qiniu_Http_Domain + '/default-avatars-02.png',
        Qiniu_Http_Domain + '/default-avatars-03.png',
        Qiniu_Http_Domain + '/default-avatars-04.png',
        Qiniu_Http_Domain + '/default-avatars-05.png',
        Qiniu_Http_Domain + '/default-avatars-06.png',
        Qiniu_Http_Domain + '/default-avatars-07.png',
        Qiniu_Http_Domain + '/default-avatars-08.png',
        Qiniu_Http_Domain + '/default-avatars-09.png'
      ],
      avatarSubscript: 0,
      form: {
        username: null,
        password: null,
        avatar: 1,
        email: null,
        captcha: null,
        captchaid: null
      }
    };
  },
  methods: {
    changeDefaultAvatar(key) {
      if (key === 'left') {
        if (this.avatarSubscript === 0) {
          this.avatarSubscript = 8;
          return;
        }
        this.avatarSubscript = this.avatarSubscript - 1;
        return;
      }
      if (this.avatarSubscript === 8) {
        this.avatarSubscript = 0;
        return;
      }
      this.avatarSubscript = this.avatarSubscript + 1;
    },
    //  注册
    regist(registerValidation) {
      this.form.avatar = this.avatarSubscript + 1;
      if (registerValidation.valid) {
        this.registAction(this.form);
        this.form.captcha = '';
      };
    }
  },
  created() {
    this.getCaptchaIdAction();
  }
};
</script>
<style>
.signupbox{
  padding-top: 20px;
}
.signup-form {
  margin:auto;
  width: 220px;
}
.signup-form h4{
  text-align: center;
}
.signup-form .select-avatar{
  width: 220px;
  margin: auto;
  text-align: center;
}

.signup-form .select-avatar img{
  border-radius: 50%;
}

.signup-form .clickicon{
    margin-top: 6%;
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background-color: #e2e2e2;
    color: #fff;
    text-align: center;
    line-height: 40px;
    cursor: pointer;
}

.signup-form .suc {
  color: #16A085;
}

.signup-form .clickicon:hover{
  background-color: #16A085;
}

.signup-form .captcha {
  text-align: center;
}

.signup-form .captcha img {
  border-radius: 4px;
  border:1px solid #b1b0b0;
  cursor: pointer;
}
</style>