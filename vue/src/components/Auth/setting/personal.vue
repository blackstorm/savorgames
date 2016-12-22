<template>
  <div class="box">
    <div class="columns">

      <!-- 修改昵称 -->
      <div class="column col-9">
        <form class="form-horizontal" action="#forms">
          <div class="form-group">
            <div class="col-sm-2">
              <label class="form-label" for="input-example-4">用户名</label>
            </div>
            <div class="col-sm-9">
              <input class="form-input" type="text" :value="userStore.username" readonly="readonly">  
              <p class="note">您可以使用用户名进行登录，用户名不允许被修改</p>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2">
              <label class="form-label">昵称</label>
            </div>
            <div class="col-sm-9">
              <validator name="nickNameValidation">
                <div class="toast toast-danger" v-if:="$nickNameValidation.nickname.invalid ? true : false">
                  <i class="fa fa-exclamation-circle" aria-hidden="true"></i> 您的昵称最少1位最多6位
                </div>
                <input class="form-input" :value="userStore.nickname" v-model="user.nickname" v-validate:nickname="{ required: true, minlength: 1, maxlength: 6 }">
              </validator>
              <p class="note">个人资料链接为：savorgames.com/member/{{user.nickname}}</p>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-2"></div>
            <div class="col-sm-9"><button class="btn btn-block" :disabled="$nickNameValidation.invalid" @click.prevent="OnNickNameUploadClick()">更新</button></div>
          </div>
        </form>
      </div>

      <!-- 选择头像 -->
      <div class="column col-3">
        <figure class="avatar avatar-xl">
          <img :src="'//' + userStore.avatar + '?imageMogr2/thumbnail/72x'"></figure>
        <figure class="avatar avatar-lg">
          <img :src="'//' + userStore.avatar + '?imageMogr2/thumbnail/72x'"></figure>
        <figure class="avatar">
          <img :src="'//' + userStore.avatar + '?imageMogr2/thumbnail/72x'"></figure>
        <!-- <input type="file" class="J_uploadImage" @click="showUploadModel()" data-dest="img">开始上传</input> -->
        <input type="file" class="J_uploadImage" data-dest="img" @click="showUploadModel()" />
        <p>图片大小请不要超过 2 MB，文件名请不要包括中文或者特殊字符，图像大小请随意。注意： 请尽量选择500像素左右的图片</p>
      </div>
    </div> <!--columns end-->

    <!--  莫太狂 上传 -->
    <div id="avatar-md" class="pb2md fade-in-scale" v-bind:class="{'pb2md-show': modelShow}">
      <Upload></Upload>
    </div>
    <div class="pb2md-overlay" @click.prevent="hiddenUploadModel()"></div>

  </div><!--box end-->
</template>
<script>
import ModelUtils from '../../../utils/model';
import Upload from './personal/upload.vue';
import { uploadNickNameAction } from '../../../vuex/actions';

export default {
  components: { Upload },
  data() {
    return {
      modelShow: false,
      user: {
        nickname: ''
      }
    };
  },
  methods: {
    showUploadModel() {
      this.modelShow = true;
      ModelUtils.show(document.getElementById('avatar-md'));
    },
    hiddenUploadModel() {
      ModelUtils.hidden(document.getElementById('avatar-md'));
      this.modelShow = false;
    },
    OnNickNameUploadClick() {
      if (this.userStore.nickname === this.user.nickname) return;
      this.uploadNickNameAction(this.user);
    }
  },
  vuex: {
    getters: {
      userStore: ({Auth}) => Auth.user
    },
    actions: {
      uploadNickNameAction
    }
  },
  ready() {}
};
</script>
<style>
.settingbox .box {
    width: 70%;
    margin: auto;
    padding-top: 70px;
}
.settingbox .box .note{
    color:#747474;
}

/*模态框*/
.pb2md {
    position: fixed;
    /*top: 50%;*/
    top: 56%;
    left: 50%;
    width: 50%;
    height: auto;
    z-index: 2100;
    visibility: hidden;
    -webkit-backface-visibility: hidden;
    -moz-backface-visibility: hidden;
    backface-visibility: hidden;
}
.pb2md-show {
  visibility: visible;
}
.pb2md-overlay {
    position: fixed;
    width: 100%;
    height: 100%;
    visibility: hidden;
    top: 0;
    left: 0;
    z-index: 1000;
    opacity: 0;
    background: rgba(30,30,30,0.5);
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
/*        -webkit-filter: blur(10px);  
       -moz-filter: blur(10px);  
        -ms-filter: blur(10px);      
            filter: blur(10px);*/
}
body.pb2md-show .pb2md-overlay {
    opacity: 1;
    visibility: visible;
}

#avatar-md {
  width:auto;
  height:auto;
}

.avatarplane{
  width: 100%;
  background-color: #fff;
  border-radius: 5px;
  box-shadow:0 5px 10px rgba(0,0,0,.15);
  padding:30px;
}

.pb2md-show.fade-in-scale .plane {
    -webkit-transform: scale(1);
    -moz-transform: scale(1);
    -ms-transform: scale(1);
    transform: scale(1);
    opacity: 1;
}
.fade-in-scale .plane {
    -webkit-transform: scale(0.7);
    -moz-transform: scale(0.7);
    -ms-transform: scale(0.7);
    transform: scale(0.7);
    opacity: 0;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
}
</style>