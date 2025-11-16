module.exports = {
  apps: [
    {
      name: 'akshar-backend',
      script: 'server.js',
      instances: 1,
      exec_mode: 'fork',
      env: {
        NODE_ENV: 'production',
        PORT: 3009
      },
      max_restarts: 3,
      restart_delay: 5000
    }
  ]
};


