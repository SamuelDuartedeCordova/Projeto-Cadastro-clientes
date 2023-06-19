package com.example.demo.services;

import com.example.demo.database.ConexaoDataBase;
import com.example.demo.models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteServices {
    public static ConexaoDataBase conexaoDataBase = new ConexaoDataBase();

    public static List<Cliente> listarCliente() throws SQLException, ClassNotFoundException{
        List<Cliente> out =new ArrayList<>();
        Connection conn = conexaoDataBase.getConnetion();
        Statement sta = conn.createStatement();
        ResultSet resultSet = sta.executeQuery("SELECT * FROM clientes;");

        while (resultSet.next()){
            Cliente cli = new Cliente();
            cli.setId(resultSet.getInt(1));
            cli.setDocumento(resultSet.getString(2));
            cli.setNome(resultSet.getString(3));
            cli.setRg(resultSet.getString(4));
            cli.setEmail(resultSet.getString(5));
            cli.setTelefone(resultSet.getString(6));

            out.add(cli);
        }
            return out;
    }
    public static void salvarCliente (Cliente cli){
        System.out.println("teste salvar cliente bd pré");
        try {
            Connection conn = conexaoDataBase.getConnetion();

            String insertSQL = "insert into clientes (documento, nome, rg, email, telefone) values (?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(insertSQL);

            ps.setString(1, cli.getDocumento());
            ps.setString(2, cli.getNome());
            ps.setString(3, cli.getRg());
            ps.setString(4, cli.getEmail());
            ps.setString(5, cli.getTelefone());

            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("teste salvar cliente bd pós");
    }

    public static boolean excluirCliente (int idCliente){
        System.out.println("teste excluir cliente bd pre");

        try {
            Connection conn = conexaoDataBase.getConnetion();
            String deleteSQL =" delete from clientes where id = ?";
            PreparedStatement ps =conn.prepareStatement(deleteSQL);
            ps.setInt(1, idCliente);

            return ps.execute();

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("teste excluir cliente bd pos");

        return false;


    }

    public static boolean atualizarCliente (int idCliente, Cliente cli){
        System.out.println("teste atualizar cliente bd pre");
        try {

            Connection conn = conexaoDataBase.getConnetion();
            String updateSql = "update clientes set documento=?, nome=?, RG=?, email=?, telefone=? where id = ?";

            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setInt(1, idCliente);
            ps.setString(2, cli.getDocumento());
            ps.setString(3, cli.getNome());
            ps.setString(4, cli.getRg());
            ps.setString(5, cli.getEmail());
            ps.setString(6, cli.getTelefone());
            
            System.out.println("teste excluir cliente bd pos");

            return ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    public static boolean buscarClientePorDocumento (String documento) {
        try {
            Connection conn = conexaoDataBase.getConnetion();

            String selectSql = "select id from clientes where documento= '" + documento + "'";

            Statement sta = conn.createStatement();
            ResultSet rs = sta.executeQuery(selectSql);

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
