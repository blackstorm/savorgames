import cookie from 'react-cookie';
import { CookieDomain } from '../config.js';

let cookieConfig = {};

if (CookieDomain !== '') {
  cookieConfig = {domain: CookieDomain};
}

export function saveCookie(name, value) {
  // window.alert('保存token');
  cookie.save(name, value, cookieConfig);

  // 修复保存cookie的BUG
  if (name === 'token') {
    if (!hasToken()) {
      document.cookie = 'token=' + value;
    };
  };
}

export function removeCookie(name) {
  // window.alert('删除cookie');
  cookie.remove(name, cookieConfig);
}

export function signOut() {
  cookie.remove('token', cookieConfig);
}

export function isLogin() {
  return !!cookie.load('token');
}

export function getCookie(name) {
  return cookie.load(name);
}

export function hasToken() {
  var token = cookie.load('token');
  // cookie 不为空 不为null 不是 undefined
  if (token !== '' && token !== null && typeof token !== 'undefined') {
    // window.alert('you token');
    return true;
  }
  // window.alert('没有token');
  return false;
}
