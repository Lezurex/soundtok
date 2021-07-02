module.exports = {
    transpileDependencies: ['@vue/reactivity'],
    devServer: {
        proxy: {
            "^/api": {
                target: "http://localhost:8080",
                changeOrigin: true
            }
        }
    }
}