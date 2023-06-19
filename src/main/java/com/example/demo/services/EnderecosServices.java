package com.example.demo.services;

import com.example.demo.database.ConexaoDataBase;
import com.example.demo.models.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecosServices {
    public static ConexaoDataBase conexaoDataBase = new ConexaoDataBase();

    public static List<Endereco> listarEnderecos() throws SQLException, ClassNotFoundException{
        List<Endereco> out = new ArrayList<>();
        Connection conn = conexaoDataBase.getConnetion();
        Statement sta = conn.createStatement();
        ResultSet resultSet = sta.executeQuery("SELECT * FROM public.enderecos;");

        while (resultSet.next()){
            Endereco end = new Endereco();
            end.setId(resultSet.getInt(1));
            end.setCodigoCliente(resultSet.getString(2));
            end.setCep(resultSet.getString(3));
            end.setEstado(resultSet.getString(4));
            end.setCidade(resultSet.getString(5));
            end.setBairro(resultSet.getString(6));
            end.setRua(resultSet.getString(7));
            end.setNumero(Integer.parseInt(resultSet.getString(8)));

            out.add(end);
        }
        return out;
    }

    public static void salvarEndereco (Endereco end) {
        try {
            Connection conn = conexaoDataBase.getConnetion();

            String insertSQL = "insert into public.enderecos (codigo_cliente, cep, estado, cidade, bairro, rua, numero) values (?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(insertSQL);
            ps.setString(1, end.getCodigoCliente());
            ps.setString(2, end.getCep());
            ps.setString(3, end.getEstado());
            ps.setString(4, end.getCidade());
            ps.setString(5, end.getBairro());
            ps.setString(6, end.getRua());
            ps.setInt(7, Integer.parseInt(String.valueOf(end.getNumero())));

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean excluirEndereco (int idEndereco){
        try {
            Connection conn = conexaoDataBase.getConnetion();
            String deleteSQL =" delete from enderecos where id = ?";
            PreparedStatement ps =conn.prepareStatement(deleteSQL);
            ps.setInt(1,idEndereco);

            return ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean atualizarEndereco (int idEndereco, Endereco end ) {
        try {


            Connection conn = conexaoDataBase.getConnetion();

            String update = "update enderecos set codigoCliente=?, cep=?, estado=?, cidade=?, bairro=?, rua=?, numero=? where id = ?";

            PreparedStatement ps = conn.prepareStatement(update);
            ps.setInt(1, idEndereco);
            ps.setString(2, end.getCodigoCliente());
            ps.setString(3, end.getCep());
            ps.setString(4, end.getEstado());
            ps.setString(5, end.getCidade());
            ps.setString(6, end.getBairro());
            ps.setString(7, end.getRua());
            ps.setInt(8, Integer.parseInt(String.valueOf(end.getNumero())));

            return ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean buscarEnderecoPorCodigoCliente (String codigoCliente) {
        try {
            Connection conn = conexaoDataBase.getConnetion();

            String selectSql = "select id from enderecos where documento= '" + codigoCliente + "'";

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery(selectSql);

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
