import cookie from 'react-cookie';

var homeBannerCookieName = 'homeBannerShow';
var homeBannerCookieDay = 14;
var registerBannerCookieName = 'registerBannerShow';
var registerBannerCookieDay = 5;

// function getExp(day) {
//   var exp = new Date();
//   return exp.setTime(exp.getTime() + day * 24 * 60 * 60 * 1000);
// }

export function setHomeBannerCookie(value) {
  var exp = new Date();
  exp.setTime(exp.getTime() + homeBannerCookieDay * 24 * 60 * 60 * 1000);
  // var exp = getExp(homeBannerCookieDay);
  cookie.save(homeBannerCookieName, value, {expires: exp});
};

export function getHomeBannerCookie() {
  return cookie.load(homeBannerCookieName);
};

export function setRegisterBannerCookie(value) {
  var exp = new Date();
  exp.setTime(exp.getTime() + registerBannerCookieDay * 24 * 60 * 60 * 1000);
  cookie.save(registerBannerCookieName, value, {expires: exp});
};

export function getRegisterBannerCookie() {
  var value = cookie.load(registerBannerCookieName);
  if (value === null || value === undefined) {
    return null;
  }
  return value;
};
