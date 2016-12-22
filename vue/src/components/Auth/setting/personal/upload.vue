<template>
      <div class="avatarplane plane">
        <div class="cropper">
          <div class="cropper_box" id="cropperBox">
            <img src="data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7" style="width:100px;height:100px;" id="uploadPreview"></div>
          <div class="cropper_res_wrap">
            <div class="cropper_res" id="cropperRes">
            </div>
          </div>
        </div>
        <button class="btn btn-block" v-show:="img == null ? false : true" @click.prevent="finishCropImage()" v-bind:class="{'loading': isApload}" :disabled="isApload">
         完成
        </button>
      </div>
</template>
<script>
import { AvatarUploadApi, Qiniu_UploadUrl } from '../../../../api/resources';
import { uploadAvatarCallBackAction } from '../../../../vuex/actions';
import Cropper from 'cropperjs';

export default {
  vuex: {
    actions: {
      uploadAvatarCallBackAction
    }
  },
  data() {
    return {
      isApload: false,
      cropper: null,
      img: null,
      flag: {
        imgHasLoad: false,
        cropperHasInit: false
      },
      qiniu: {
        token: null,
        key: null,
        blob: null
      },
      helper: {
        support: !!(window.FileReader && window.CanvasRenderingContext2D && (window.URL || window.webkitURL)),
        isImage: function(type) {
          var filter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
          return !!(filter.test(type));
        }
      }
    };
  },
  methods: {
    bindLoadImgEvent() { // 绑定监听事件
      var thisObj = this;
      if (!thisObj.helper.support) return;
      var uploadPreview = document.getElementById('uploadPreview');
      var uploadImages = document.querySelectorAll('.J_uploadImage');
      var reader = new FileReader();
      var URL = window.URL || window.webkitURL;
      var blobURL;
      var len = uploadImages.length;
      for (var i = 0; i < len; i++) {
        uploadImages[i].addEventListener('change',
        function() {
          var files = this.files;
          if (files.length === 0) return;
          var file = files[0];
          if (!thisObj.helper.isImage(file.type)) {
            alert('you have to select an image file!');
            return;
          }
          reader.readAsDataURL(file);
          blobURL = URL.createObjectURL(file);
          if (thisObj.cropper) {
            thisObj.cropper.reset();
          }
          thisObj.flag.imgHasLoad = true; // 设置img已经选择完成
          thisObj.img = this.dataset.dest; // 设置img标识
        });
      }

      reader.onload = function(e) {
        uploadPreview.src = e.target.result;
        if (!thisObj.flag.cropperHasInit) {
          thisObj.loadCropper(); // 去load图片
          return;
        }
        thisObj.cropper.replace(blobURL);
      };
    },
    loadCropper: function() { // 加载裁剪工具
      var thisObj = this;
      var image = document.querySelector('#cropperBox > img');
      var option = {
        aspectRatio: 1 / 1,
        preview: '.cropper_res' // 预览图片的位置
      };
      thisObj.cropper = new Cropper(image, option);
      thisObj.flag.cropperHasInit = true;
    },
    finishCropImage() { // 截图
      var thisObj = this;
      var croppedCanvas = thisObj.cropper.getCroppedCanvas({
        width: 72,
        height: 72
      });
      croppedCanvas.toBlob(function(blob) {
        thisObj.qiniu.blob = blob;
        console.log(thisObj.qiniu.blob);
        thisObj.onSubmit();
      });
    },
    onSubmit() {
      this.isApload = true;
      if (this.qiniu.blob === null) {
        window.alert('请稍等，正在处理图片');
        return;
      };
      if (this.qiniu.token === null || this.qiniu.key === null) {
        window.alert('抱歉，可能上传令牌没有获取到，请刷新尝试获取');
        return;
      };
      var formData = new FormData();
      formData.append('token', this.qiniu.token);
      formData.append('file', this.qiniu.blob);
      formData.append('key', this.qiniu.key);
      this.$http.post(Qiniu_UploadUrl, formData, {credentials: false, headers: {'Content-Type': 'multipart/form-data'}}).then((response) => {
        console.log(response.data);
        this.uploadAvatarCallBackAction(response.data);
        this.isApload = false;
      }, response => {
        window.alert(response.data.error);
      });
    }
  },
  ready() {
    // 获取图片token
    this.$http.get(AvatarUploadApi).then((response) => {
      if (!response.ok || response.data.status !== 'success') {
        window.alert('获取token失败:' + response.data.message);
        return;
      }
      this.qiniu.token = response.data.token;
      this.qiniu.key = response.data.key;
    }, (response) => {
      window.alert('获取token失败');
    });

    // 初始化
    this.bindLoadImgEvent();
  }
};
</script>
<style>
.cropper{-webkit-box-sizing:border-box;box-sizing:border-box}
.cropper{position:relative;width:600px;height:460px;padding:80px 150px;background-color:#eee}
.cropper .cropper_box{width:300px;height:300px}
.cropper .cropper_res_wrap{position:absolute;top:0;right:0;width:130px;height:130px;padding:15px;background-color:#f8f8f8}
.cropper .cropper_res_wrap .cropper_res{
  width:100px;
  height:100px;
  overflow:hidden;
  border:1px solid #eee;
  background-color:#fff;
}
</style>