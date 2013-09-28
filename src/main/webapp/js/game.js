(function () {
    var Sprite = function (imgSrc) {
        this.img = new Image();
        var that = this;
        this.img.onload = function () {
            that.width = that.img.width;
            that.height = that.img.height;
        }
        this.img.src = imgSrc;
    }
    Sprite.prototype = {
        draw: function (x, y) {
            game.context.drawImage(this.img, x, y);
        }
    }

    var Fireball = function (x, y, vX, vY) {
        this.sprite = new Sprite("/img/damager.png");
        this.x = x;
        this.y = y;
        this.velocityX = vX;
        this.velocityY = vY;
    }
    Fireball.prototype = {
        calculateVelocity: function (toX, toY) {
            var X = (toX - this.x),
                Y = (toY - this.y),
                power = Math.sqrt(X * X + Y * Y);

            this.velocityX = 9 * X / power;
            this.velocityY = 9 * Y / power;
        },
        draw: function (x, y) {
            this.sprite.draw(this.x, this.y);
        }
    }

    var Player = function (x, y, vX, vY) {
        this.spriteR = new Sprite("img/playerR.png");
        this.spriteL = new Sprite("img/playerL.png");
        this.x = x;
        this.y = y;
        this.direction = true;
        this.velocityX = vX;
        this.velocityY = vY;
        this.health = this.HEALTH_MAX;
    }
    Player.prototype = {
        VELOCITY_X: 9,
        VELOCITY_Y: 10,
        HEALTH_MAX: 100,
        draw: function () {
            if (this.direction) {
                this.spriteR.draw(this.x, this.y);
            }
            else {
                this.spriteL.draw(this.x, this.y);
            }

        },
        jump: function () {
            this.y -= 1;
            this.velocityY = -this.VELOCITY_Y;
        },
        right: function () {
            this.velocityX = this.VELOCITY_X;
        },
        left: function () {
            this.velocityX = -this.VELOCITY_X;
        },
        direct: function () {
            var vX = this.velocityX;
            if(vX > 0) {
                this.direction = true;
            }
            else if(vX < 0) {
                this.direction = false;
            }
        },
        move: function () {
            var vX = this.velocityX,
                vY = this.velocityY,
                ret = true;
            this.x += vX;
            this.y += vY;

            // нужна ли отправка нового положения на сервер
            if (vX == 0 && vY == 0) {
                ret = false;

            }
            // попытка сохранить горизонтальную скорость при прыжке
            if (vY >= 0) {
                if (vX < 0) {
                    this.velocityX = Math.ceil(vX / 2);
                    this.direction = false;
                }
                else if (vX > 0) {
                    this.velocityX = Math.floor(vX / 2);
                    this.direction = true;
                }
            }
            return ret;
        }
    }

    var Game = function (canvasId, width, height) {
        this.canvas = document.getElementById(canvasId);
        this.canvas.width = width;
        this.canvas.height = height;

        this.context = this.canvas.getContext("2d");
        this.background = {
            img: new Image(),
            x: 0,
            y: 0
        }

        // для расчета коллизий
        this.boxes = null;
        this.box = [0, 0, 0, 0];

        // шарики
        this.fireballs = [];
        this.enemies = [];

        this.healthElement = document.getElementById("health");

        this.fps = 60;
        this.fpsElement = document.getElementById("fps");
        this.lastAnimationFrameTime = 0;
        this.lastFpsUpdateTime = 0;
    }
    Game.prototype = {
        init: function (player, background, boxes) {
            this.player = player;
            this.boxes = boxes;

            // загрузка всех изображений
            this.background.img.onload = function () {
                requestAnimationFrame(game.animate, game.canvas);
            }
            this.background.img.src = background;
            callbackInit();
        },
        animate: function (now) {
            game.fps = game.calculateFps(now);
            game.healthElement.innerHTML = game.player.health;

            game.update();
            game.draw();
            requestAnimationFrame(game.animate, game.canvas);
        },
        update: function () {
            var player = this.player;
            // гравитация
            player.velocityY += 1;

            var i,
                bb,
                box,
                height = player.spriteR.height,

                x = player.x,
                y = player.y + height,
                Y = y + player.velocityY,
                X = x + player.spriteR.width / 2,
                balls = this.fireballs,
                enems = this.enemies;

            // расчет коллизий
            if (y == this.box[1] && this.box[0] <= x && X <= this.box[2]) {
                player.velocityY = 0;
            }
            else {
                for (bb = this.boxes, i = bb.length; i--;) {
                    box = bb[i];
                    if (box[0] <= x && X <= box[2]) {
                        if (y <= box[3] && Y >= box[3]) {
                            player.velocityY = 0;
                            player.y = box[1] - height - 1;
                            if (this.box != box) {
                                this.box = box;
                                console.log(x, X, y, Y, box);
                            }
                            break;
                        }
                    }
                }
            }

            // todo убрать заглушку для гравитации! или отправить проиграл на сервер!
            //if (player.y >= 420) {
            //    if (player.velocityY > 0) {
            //        player.velocityY = 0;
            //    }
            //}

            //var balls_new = [];

            // движение шаров
            for (i = balls.length; i--;) {
                bb = balls[i];
                //if (bb.x < 0 || bb.x > this.canvas.width) {
                //    continue;
                //}
                //if (bb.y < 0 || bb.y > this.canvas.height) {
                //    continue;
                //}

                bb.x += bb.velocityX;
                bb.y += bb.velocityY;
                //balls_new[balls_new.length] = bb;
            }
            //this.fireballs = balls_new;

            // расчет направления противников
            for(i = enems.length; i--;) {
                enems[i].direct();
            }

            // движение игрока
            if (player.move()) {
                // отправляем на сервер, если были измения
                $.post("/", {
                    game: true,
                    action: "position",
                    positionX: player.x,
                    positionY: player.y,
                    velocityX: player.velocityX,
                    velocityY: player.velocityY
                });
            }

        },
        draw: function () {
            var bg = this.background,
                can = this.canvas,
                i = 0,
                balls = this.fireballs,
                enems = this.enemies;
            this.context.drawImage(bg.img, 0, 0, bg.img.width, bg.img.height, 0, 0, can.width, can.height);
            this.player.draw();
            for (i = enems.length; i--;) {
                enems[i].draw();
            }
            for (i = balls.length; i--;) {
                balls[i].draw();
            }
        },
        calculateFps: function (now) {
            if (this.lastAnimationFrameTime === 0) {
                this.lastAnimationFrameTime = now;
                return 60;
            }
            var fps = 1000 / (now - this.lastAnimationFrameTime);
            this.lastAnimationFrameTime = now;
            if (now - this.lastFpsUpdateTime > 1000) {
                this.lastFpsUpdateTime = now;
                this.fpsElement.innerHTML = fps.toFixed(0) + ' fps';
            }
            return fps;
        },
        addFireball: function (x, y) {
            var balls = this.fireballs,
                pl = this.player,
                pX = pl.x,
                sprt = pl.spriteR,
                bX;
            if (x > pX) {
                pl.direct = true;
            }
            else {
                pl.direct = false;
            }

            if (pl.direct) {
                bX = pX + sprt.width + 5;
            }
            else {
                bX = pX - 5;
            }
            var fb = new Fireball(bX, pl.y + sprt.height / 3, null, null);
            fb.calculateVelocity(x, y);
            balls[balls.length] = fb;
            return fb;
        }
    }
    function callbackInit() {
        window.onkeydown = function (e) {
            if(!inPlay) {
                return;
            }
            var k = e.keyCode;

            switch (k) {
                case 65:
                    game.player.left();
                    break;
                case 87:
                    game.player.jump();
                    break;
                case 68:
                    game.player.right();
                    break;
                case 83:
                    // down
                    break;
            }
        }
        window.onmousedown = function (e) {
            if(!inPlay) {
                return;
            }
            var rect = game.canvas.getBoundingClientRect(),
                x = e.clientX - rect.left,
                y = e.clientY - rect.top,
                fb = game.addFireball(x, y);
            // отправить на сервер
            $.post("/", {
                game: true,
                action: "fireball",
                positionX: fb.x,
                positionY: fb.y,
                velocityX: fb.velocityX,
                velocityY: fb.velocityY
            });
        }
    }


    // инстанц игры, используется в функции вызова анимации
    var game, globalTimer, inPlay;

    $.getJSON("/data/map1.json", function (data) {
        game = new Game("paper", data.width, data.height);

        var player = new Player(350, 100, 0, 0);

        game.init(player, "/img/" + data.img, data.boxes);

        console.log("GAME START");
        inPlay = true;
        globalTimer = setInterval(function () {
            $.post("/", {
                game: true,
                action: "get"
            }, function (data) {
                if(!inPlay) {
                    return;
                }

                if(data === false) {
                    clearInterval(globalTimer);
                    inPlay = false;
                    window.location.reload();
                    return;
                }

                var sballs = data[0],
                    senems = data[1],
                    pl = data[2],
                    fballs = game.fireballs,
                    enemies = game.enemies,
                    m = fballs.length,
                    i, n, f, ff;
                // изменяем здоровье игрока
                game.player.health = pl[4];


                // перезаписываем координаты для всех фаерболов
                for (i = 0, n = sballs.length; i < n; ++i) {
                    f = sballs[i];
                    if(i >= m) {
                        fballs[i] = new Fireball(f[0], f[1], f[2], f[3]);
                    }
                    else {
                        ff = fballs[i];
                        ff.x = f[0];
                        ff.y = f[1];
                        ff.velocityX = f[2];
                        ff.velocityY = f[3];
                    }
                }
                // обрезаем массив в случае исчезновения фаерболов
                if(m > n) {
                    game.fireballs = [];
                    for(i = 0; i < n; ++i) {
                        game.fireballs[i] = fballs[i];
                    }
                }
                // с игроками тоже самое
                m = enemies.length;
                for(i = 0, n = senems.length; i < n; ++i) {
                    f = senems[i];
                    if(i >= m) {
                        ff = new Player(f[0], f[1], f[2], f[3]);
                        ff.health = f[4];
                        enemies[i] = ff;
                    }
                    else {
                        ff = enemies[i];
                        ff.x = f[0];
                        ff.y = f[1];
                        ff.velocityX = f[2];
                        ff.velocityY = f[3];
                    }
                }
                if(m > n) {
                    game.enemies = [];
                    for(i = 0; i < n; ++i) {
                        game.enemies[i] = enemies[i];
                    }
                }
            }, "json");
        }, 50);
    });
})();