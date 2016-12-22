<template>
  <vue-toast v-ref:toast></vue-toast>
</template>

<script>
import vueToast from 'vue-toast';
import { showMsg, hideMsg } from '../../vuex/actions';

export default {
  components: { vueToast },
  vuex: {
    getters: {
      msg: ({showmsg}) => showmsg.message
    },
    actions: {
      showMsg, hideMsg
    }
  },
  watch: {
    'msg': {
      handler: function(val, oldVal) {
        if (val.content !== '') {
          this.showToastr(val.content, val.type);
          this.hideMsg();
        }
      },
      deep: true
    }
  },
  methods: {
    showToastr(content, type = 'error', position = 'top right') {
      this.$refs.toast.setOptions({ maxToasts: 5, position: position });
      this.$refs.toast.showToast(content, {
        theme: type,
        timeLife: 2000,
        closeBtn: false
      });
    }
  }
};
</script>
<style>
.vue-toast-manager_container.\--top {
  top:10px
}
.vue-toast-manager_container.\--bottom {
  bottom:10px
}
.vue-toast-manager_container.\--left {
  left:10px
}
.vue-toast-manager_container.\--right {
  right:10px
}
.vue-toast-manager_toasts {
  position:relative;
}
.vue-toast_container {
  position:absolute;
  padding-bottom:10px;
  -webkit-transform:translateY(0);
  transform:translateY(0);
  -webkit-transition:opacity .3s ease-out,-webkit-transform .2s ease-out;
  transition:opacity .3s ease-out,-webkit-transform .2s ease-out;
  transition:transform .2s ease-out,opacity .3s ease-out;
  transition:transform .2s ease-out,opacity .3s ease-out,-webkit-transform .2s ease-out;
  -webkit-backface-visibility:hidden;
  backface-visibility:hidden
}
.vue-toast_container._default .vue-toast_message {
  background-color:rgba(0,0,0,.9)
}
.vue-toast_container._info .vue-toast_message {
  background-color:rgba(49,112,143,.9)
}
.vue-toast_container._success .vue-toast_message {
  background-color:rgba(60,118,61,.9)
}
.vue-toast_container._warning .vue-toast_message {
  background-color:rgba(138,109,59,.9)
}
.vue-toast_container._error .vue-toast_message {
  background-color:#e85600;
}
.vue-toast-manager_container.\--top .vue-toast_container {
  top:0
}
.vue-toast-manager_container.\--bottom .vue-toast_container {
  bottom:0
}
.vue-toast-manager_container.\--left .vue-toast_container {
  left:0
}
.vue-toast-manager_container.\--right .vue-toast_container {
  right:0
}
.vue-toast_container.v-enter,.vue-toast_container.v-leave {
  opacity:0
}
.vue-toast_message {
  border: .1rem solid #eaeaea;
  border-radius: .3rem;
  padding: 1.4rem;
  color:#fff;
}
.vue-toast_close-btn {
  cursor:pointer;
  position:absolute;
  right:5px;
  top:5px;
  width:14px;
  height:14px;
  opacity:.7;
  -webkit-transition:opacity .15s ease-in-out;
  transition:opacity .15s ease-in-out;
  -webkit-backface-visibility:hidden;
  backface-visibility:hidden
}
.vue-toast_close-btn:hover {
  opacity:.9
}
.vue-toast_close-btn:after,.vue-toast_close-btn:before {
  content:'';
  position:absolute;
  top:6px;
  width:14px;
  height:2px;
  background-color:#fff
}
.vue-toast_close-btn:before {
  -webkit-transform:rotate(45deg);
  transform:rotate(45deg)
}
.vue-toast_close-btn:after {
  -webkit-transform:rotate(-45deg);
  transform:rotate(-45deg)
}
.vue-toast-manager_container.\--top {
  top:45px
}
</style>
