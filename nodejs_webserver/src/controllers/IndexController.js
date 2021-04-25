module.exports = {
    async index(req, res) {
        return res.send(`
            <html>
                <head>
                    <script data-ad-client="ca-pub-0914499244934276" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                </head>
            <body>API v1.0</body>
            </html>
        `);
    },
}