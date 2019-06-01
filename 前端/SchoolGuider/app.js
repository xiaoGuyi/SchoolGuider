//app.js
App({
  onLaunch: function () {
    this.hidetabbar();
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })

    wx.getSystemInfo({
      success: (res) => {
        this.globalData.height = res.statusBarHeight
      }
    });
  },
  hidetabbar() {
    wx.hideTabBar({
      fail: function () {
        setTimeout(function () { // 做了个延时重试一次，作为保底。
          wx.hideTabBar()
        }, 500)
      }
    });
  },
  editTabbar: function () {
    let tabbar = this.globalData.tabBar;
    let currentPages = getCurrentPages();
    let _this = currentPages[currentPages.length - 1];
    let pagePath = _this.route;
    (pagePath.indexOf('/') != 0) && (pagePath = '/' + pagePath);
    for (let i in tabbar.list) {
      tabbar.list[i].selected = false;
      (tabbar.list[i].pagePath == pagePath) && (tabbar.list[i].selected = true);
    }
    _this.setData({
      tabbar: tabbar
    });
  },

  globalData: {
    userInfo: null,
    recordId: 0,
    index:0,
    scenicNames: "",
    introduces: "",
    imageNames: "",
    voiceNames: "",

    share: false,  // 分享默认为false
    height: 0,

    systemInfo: null,//客户端设备信息
    tabBar: {
      "backgroundColor": "#ffffff",
      "color": "#000000",
      "selectedColor": "#058ffa",
      "list": [
        {
          "pagePath": "/pages/introduce/introduce",
          "iconPath": "../../images/introduce1.jpg",
          "selectedIconPath": "../../images/introduce2.jpg",
          "text": "景点介绍"
        },
        {
          "pagePath": "/pages/map/map",
          "iconPath": "../../images/navigate.jpg",
          "isSpecial": true,
          "text": "校园地图"
        },
        {
          "pagePath": "/pages/profile/profile",
          "iconPath": "../../images/profile1.jpg",
          "selectedIconPath": "../../images/profile2.jpg",
          "text": "我的主页"
        }
      ]
    }
  }
})