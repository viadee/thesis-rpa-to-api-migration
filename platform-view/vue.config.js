module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  chainWebpack: config => {
    // GraphQL Loader
    config.module
      .rule('xml')
      .test(/\.xml$/)
      .use('raw-loader')
      .loader('raw-loader')
      .end()
  }
}
