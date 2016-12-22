<template>
<div class="container index-content">
  <div class="settingbox">
    <ul class="tab tab-block">
      <li class="tab-item " :class="{ 'active': personal }" @click.prevent="showPersonal()">
        <a href="">个人设置</a>
      </li>
      <li class="tab-item" >
        <a href="">我的资料</a>
      </li>
      <li class="tab-item" :class="{ 'active': mail }" @click.prevent="showMail()">
        <a href="">Email地址</a>
      </li>
      <li class="tab-item" :class="{ 'active': notify }" @click.prevent="showNotify()">
        <a href="">消息与通知</a>
      </li>
    </ul>
    <Personal v-if="personal"></Personal>
    <Mail v-if="mail"></Mail>
    <Notify v-if="notify"></Notify>
  </div>
</div>
</template>
<script>
import Personal from './setting/personal.vue';
import Mail from './setting/mail.vue';
import Notify from './setting/notify.vue';

export default {
  components: { Personal, Mail, Notify },
  data() {
    return {
      personal: true,
      mail: false,
      notify: false
    };
  },
  methods: {
    showPersonal() {
      this.clearTab();
      this.personal = true;
    },
    showMail() {
      this.clearTab();
      this.mail = true;
    },
    showNotify() {
      this.clearTab();
      this.notify = true;
    },
    clearTab() {
      this.personal = false;
      this.mail = false;
      this.notify = false;
    }
  }
};
</script>
<style>
.settingbox{
  margin-top: 30px;
}
</style>