package com.example.demo.controller;


import com.example.demo.models.Cliente;
import com.example.demo.models.Endereco;
import com.example.demo.services.ClienteServices;
import com.example.demo.services.EnderecosServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@FxmlView("/Trabalho_Final.fxml")
public class Controller {

    //Aba Cliente---------------------------------------------------------------------------------
    @FXML
    private TextField documento;

    @FXML
    private TextField nome;

    @FXML
    private TextField rg;

    @FXML
    private TextField email;

    @FXML
    private TextField telefone;

    @FXML
    private TableView<Cliente> tabela_Clientes;

    @FXML
    private TableColumn<Cliente, String> coluna_Documento;

    @FXML
    private TableColumn<Cliente, String> coluna_Nome;

    @FXML
    private TableColumn<Cliente, String> coluna_RG;

    @FXML
    private TableColumn<Cliente, String> coluna_Email;

    @FXML
    private TableColumn<Cliente, String> coluna_Telefone;

    private ClienteServices clienteServices = new ClienteServices();

    //Aba Endereço----------------------------------------------------------------------------------

    @FXML
    private TextField codigoCliente;

    @FXML
    private TextField cep;

    @FXML
    private TextField estado;

    @FXML
    private TextField cidade;

    @FXML
    private TextField bairro;
    @FXML
    private TextField rua;
    @FXML
    private TextField numero;

    @FXML
    private TableView<Endereco> tabelaEnderecos;

    @FXML
    private TableColumn<Endereco, String> coluna_Codigo_Cliente;

    @FXML
    private TableColumn<Endereco, String> coluna_Cep;

    @FXML
    private TableColumn<Endereco, String> coluna_Estado;

    @FXML
    private TableColumn<Endereco, String> coluna_Cidade;

    @FXML
    private TableColumn<Endereco, String> coluna_Bairro;
    @FXML
    private TableColumn<Endereco, String> coluna_Rua;
    @FXML
    private TableColumn<Endereco, String> coluna_Numero;

    private int index = -1;

    private EnderecosServices enderecosServices = new EnderecosServices();

    //Aba Cliente-----------------------------------------------------------------------------------------
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException{
        coluna_Documento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        coluna_Nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        coluna_RG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        coluna_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        coluna_Telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        List<Cliente> clienteList = clienteServices.listarCliente();

        tabela_Clientes.getItems().addAll(clienteList);


        tabela_Clientes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    try {
                        Cliente cli = tabela_Clientes.getSelectionModel().getSelectedItem();
                        documento.setText(cli.getDocumento());
                        nome.setText(cli.getNome());
                        rg.setText(cli.getRg());
                        email.setText(cli.getEmail());
                        telefone.setText(cli.getTelefone());

                        index = cli.getId();

                        System.out.println(index);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    public void salvarCliente(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Alert alertInclusao = new Alert(Alert.AlertType.CONFIRMATION);
        alertInclusao.setTitle("Confirmação de Inclusão");
        alertInclusao.setHeaderText("Confirma inclusão de registro?");

        Optional<ButtonType> retornoAlerta = alertInclusao.showAndWait();

        try {


            if (retornoAlerta.get() == ButtonType.OK) {

                System.out.println("teste salvar cliente controler pre");

                Cliente cliente = new Cliente();
                cliente.setDocumento(documento.getText());
                cliente.setNome(nome.getText());
                cliente.setRg(rg.getText());
                cliente.setEmail(email.getText());
                cliente.setTelefone(telefone.getText());


                // atualiza item - resetar index
                if (!cliente.getDocumento().equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro de Inclusão");
                    alert.setHeaderText("Documento não informado!");
                }
                if (!cliente.getNome().equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro de Inclusão");
                    alert.setHeaderText("Nome não informado!");
                }
                if (!cliente.getDocumento().matches("[0-9]*")) {
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Erro!");
                    alertErro.setHeaderText("Documento invalido! Somente valor numerico.");
                    alertErro.show();
                }
                if (!cliente.getNome().matches("[A-Z]*") && !cliente.getNome().matches("[a-z]*")){
                    Alert alertErro = new Alert(Alert.AlertType.ERROR);
                    alertErro.setTitle("Erro!");
                    alertErro.setHeaderText("Nome invalido! Somente letras.");
                    alertErro.show();
                }


                if (ClienteServices.buscarClientePorDocumento(cliente.getDocumento())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alerta");
                    alert.setHeaderText("Documento: " + documento.getText() + " já existe na base!");
                    alert.show();

                        } else if (index > -1){
                            ClienteServices.atualizarCliente(index, cliente);
                            index =-1;
                            }else {
                                ClienteServices.salvarCliente(cliente);
                }
                }

                this.carregarListaClientes();
                this.limparCampos();
            } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void excluirCliente() throws SQLException, ClassNotFoundException {
        if (index > -1) {

            Alert alertExcluirCliente = new Alert(Alert.AlertType.CONFIRMATION);
            alertExcluirCliente.setTitle("Confirmar exclusão");
            alertExcluirCliente.setHeaderText("Excluir o registro?");
            alertExcluirCliente.showAndWait().ifPresent(resposta ->{
                if (resposta == ButtonType.OK){
                    ClienteServices.excluirCliente(index);
                    this.carregarListaClientes();
                    index=-1;
                    this.limparCampos();
                }
            });

        }
    }


    //Aba Endereço-----------------------------------------------------------------------------------------


    @FXML
    public void initializeEndereco() throws SQLException, ClassNotFoundException {
        coluna_Codigo_Cliente.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        coluna_Cep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        coluna_Estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        coluna_Cidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        coluna_Bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        coluna_Rua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        coluna_Numero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        tabelaEnderecos.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    try {
                        Endereco endereco = tabelaEnderecos.getSelectionModel().getSelectedItem();
                        codigoCliente.setText(endereco.getCodigoCliente());
                        cep.setText(endereco.getCep());
                        estado.setText(endereco.getEstado());
                        cidade.setText(endereco.getCidade());
                        bairro.setText(endereco.getBairro());
                        rua.setText(endereco.getRua());
                        numero.setText(String.valueOf(endereco.getNumero()));

                        index = endereco.getId();

                        System.out.println(index);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void salvarEndereco(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Alert alertInclusao = new Alert(Alert.AlertType.CONFIRMATION);
        alertInclusao.setTitle("Confirmação de Inclusão");
        alertInclusao.setHeaderText("Confirma inclusão de registro?");

        Optional<ButtonType> retornoAlerta = alertInclusao.showAndWait();

        try {


            if (retornoAlerta.get() == ButtonType.OK) {

                System.out.println("teste salvar endereco controler pre");

                Endereco endereco = new Endereco();
                endereco.setCodigoCliente(codigoCliente.getText());
                endereco.setCep(cep.getText());
                endereco.setEstado(estado.getText());
                endereco.setCidade(cidade.getText());
                endereco.setBairro(bairro.getText());
                endereco.setRua(rua.getText());
                endereco.setNumero(Integer.parseInt(numero.getText()));


             if (index > -1){
                 tabelaEnderecos.getItems().set(index, endereco);
                    //EnderecosServices.atualizarEndereco(index, endereco);
                    index =-1;
                }else {
                    EnderecosServices.salvarEndereco(endereco);
                }
            }

            this.carregarListaEnderecos();
            this.limparCampos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excluirEndereco() {
        if (index > -1) {
            tabelaEnderecos.getItems().remove(index);
            index = -1;
            this.limparCampos();
        }
    }

    public void limparCampos() {
        documento.setText("");
        nome.setText("");
        rg.setText("");
        email.setText("");
        telefone.setText("");

    }

    public void carregarListaClientes() /*throws SQLException, ClassNotFoundException*/ {
        try {
            tabela_Clientes.getItems().remove(0, tabela_Clientes.getItems().size());
            List<Cliente> cliList = ClienteServices.listarCliente();
            tabela_Clientes.getItems().addAll(cliList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void carregarListaEnderecos() /*throws SQLException, ClassNotFoundException*/ {
        try {
            tabelaEnderecos.getItems().remove(0, tabelaEnderecos.getItems().size());
            List<Endereco> enderecoList = EnderecosServices.listarEnderecos();
            tabelaEnderecos.getItems().addAll(enderecoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}