// pages/navigate/navigate.js
//获取应用实例
const app = getApp()

Page({
  data: {
    windowWidth: '',//窗口宽度
    windowHeight: '',//窗口高度

    tabbar: {}
  },
  //事件处理函数
  bindViewTap: function () {

  },
  onLoad: function () {
    app.editTabbar();

    var that = this
    wx.showLoading({
      title: "定位中",
      mask: true
    })
    wx.getLocation({
      type: 'gcj02',
      altitude: true,//高精度定位
      //定位成功，更新定位结果
      success: function (res) {
        var latitude = res.latitude
        var longitude = res.longitude
        var speed = res.speed
        var accuracy = res.accuracy

        that.setData({
          longitude: longitude,
          latitude: latitude,
          speed: speed,
          accuracy: accuracy
        })
      },
      //定位失败回调
      fail: function () {
        wx.showToast({
          title: "定位失败",
          icon: "none"
        })
      },

      complete: function () {
        //隐藏定位中信息进度
        wx.hideLoading()
      }

    }),
      // 获取手机屏幕宽高
      wx.getSystemInfo({
        success: function (res) {
          that.setData({
            windowWidth: res.windowWidth,
            windowHeight: res.windowHeight
          })
        }
      }),
      wx.setNavigationBarColor({
        frontColor: '#ffffff',
        backgroundColor: '#00b7f3',
      })
  }
})