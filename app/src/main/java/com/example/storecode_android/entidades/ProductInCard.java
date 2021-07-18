package com.example.storecode_android.entidades;

public class ProductInCard {
    Integer idproductocarrito;
    Integer idProducto;
    String nombreProducto;
    String desProducto;
    Double precioUnitarioProducto;
    String imagenProducto;
    Integer idMarca;
    Integer idCategoria;
    Integer idUsuario;
    Integer cantidadProducto;


    public ProductInCard(){

    }

    public ProductInCard(Integer idproductocarrito,Integer idProducto, String nombreProducto, String desProducto, Double precioUnitarioProducto, String imagenProducto, Integer idMarca, Integer idCategoria, Integer idUsuario, Integer cantidadProducto) {
        this.idproductocarrito = idproductocarrito;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.desProducto = desProducto;
        this.precioUnitarioProducto = precioUnitarioProducto;
        this.imagenProducto = imagenProducto;
        this.idMarca = idMarca;
        this.idCategoria = idCategoria;
        this.idUsuario = idUsuario;
        this.cantidadProducto = cantidadProducto;
    }

    public Integer getIdproductocarrito(){
        return idproductocarrito;
    }

    public void set(Integer idproductocarrito){
        this.idproductocarrito= idproductocarrito;
    }
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDesProducto() {
        return desProducto;
    }

    public void setDesProducto(String desProducto) {
        this.desProducto = desProducto;
    }

    public Double getPrecioUnitarioProducto() {
        return precioUnitarioProducto;
    }

    public void setPrecioUnitarioProducto(Double precioUnitarioProducto) {
        this.precioUnitarioProducto = precioUnitarioProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    @Override
    public String toString() {
        return "{" +
                "idproductocarrito"+idproductocarrito+
                "idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", desProducto='" + desProducto + '\'' +
                ", precioUnitarioProducto=" + precioUnitarioProducto +
                ", imagenProducto='" + imagenProducto + '\'' +
                ", idMarca=" + idMarca +
                ", idCategoria=" + idCategoria +
                ", idUsuario=" + idUsuario +
                ", cantidadProducto=" + cantidadProducto +
                '}';
    }

    /*
    {
        "idProducto": 34,
        "nombreProducto": "Pantalla plana",
        "desProducto": "Televisión inteligente",
        "precioUnitarioProducto": 9500,
        "imagenProducto": "http://192.168.1.72:3000/public/products/image-1616629033631.jpg",
        "idMarca": 6,
        "idCategoria": 1,
        "idUsuario": 39,
        "cantidadProducto": 1
    }
     */
}
