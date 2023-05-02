const main = {
    init: function () {
        const _this = this;
        $('#btn-load-location').click(function () {
            _this.load_history();
        });
        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#btn-find').on('click', function () {
            _this.find();
        });
        $('#btn-register').on('click', function () {
            _this.registry();
        });
        $('#btn-reload').on('click', function () {
            _this.reload();
        });
    },
    load_history: function (){
        navigator.geolocation.getCurrentPosition(this.geoSuccess, this.geoError);
    },
    geoSuccess: function (){
        document.querySelector('input[name="input-rat"]').value = position.coords.latitude;
        document.querySelector('input[name="input-rnt"]').value = position.coords.longitude;
        alert("위치정보 불러오기 성공");
    },
    geoError: function (){
        alert("위치정보 불러오기 실패");
    }
}