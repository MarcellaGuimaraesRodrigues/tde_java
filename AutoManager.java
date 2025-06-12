	import java.io.*;
	import java.util.*;
	
public class AutoManager {



	    private List<Automovel> lista = new ArrayList<>();
	    private final String arquivo = "automoveis.txt";

	    public AutoManager() {
	        carregarDados();
	    }

	    public void inserirAutomovel(Automovel a) {
	        if (buscarPorPlaca(a.getPlaca()) != null) {
	            System.out.println("Já existe um automóvel com essa placa.");
	            return;
	        }
	        lista.add(a);
	        System.out.println("Automóvel inserido com sucesso.");
	    }

	    public void removerAutomovel(String placa) {
	        Automovel a = buscarPorPlaca(placa);
	        if (a != null) {
	            lista.remove(a);
	            System.out.println("Automóvel removido.");
	        } else {
	            System.out.println("Automóvel não encontrado.");
	        }
	    }

	    public void alterarAutomovel(String placa, String modelo, String marca, int ano, double valor) {
	        Automovel a = buscarPorPlaca(placa);
	        if (a != null) {
	            a.setModelo(modelo);
	            a.setMarca(marca);
	            a.setAno(ano);
	            a.setValor(valor);
	            System.out.println("Dados atualizados.");
	        } else {
	            System.out.println("Automóvel não encontrado.");
	        }
	    }

	    public void consultarAutomovel(String placa) {
	        Automovel a = buscarPorPlaca(placa);
	        if (a != null) {
	            System.out.println(a.mostrar());
	        } else {
	            System.out.println("Automóvel não encontrado.");
	        }
	    }

	    public void listarOrdenado(String criterio) {
	        Comparator<Automovel> comparador = switch (criterio.toLowerCase()) {
	            case "placa" -> Comparator.comparing(Automovel::getPlaca);
	            case "modelo" -> Comparator.comparing(Automovel::getModelo);
	            case "marca" -> Comparator.comparing(Automovel::getMarca);
	            default -> null;
	        };

	        if (comparador != null) {
	            lista.stream().sorted(comparador).forEach(a -> System.out.println(a.mostrar()));
	        } else {
	            System.out.println("Critério inválido.");
	        }
	    }

	    public void salvarDados() {
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
	            for (Automovel a : lista) {
	                bw.write(a.toString());
	                bw.newLine();
	            }
	            System.out.println("Dados salvos com sucesso.");
	        } catch (IOException e) {
	            System.out.println("Erro ao salvar dados: " + e.getMessage());
	        }
	    }

	    private void carregarDados() {
	        File f = new File(arquivo);
	        if (!f.exists()) return;
	        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
	            String linha;
	            while ((linha = br.readLine()) != null) {
	                String[] partes = linha.split(",");
	                if (partes.length == 5) {
	                    lista.add(new Automovel(
	                        partes[0],
	                        partes[1],
	                        partes[2],
	                        Integer.parseInt(partes[3]),
	                        Double.parseDouble(partes[4])
	                    ));
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Erro ao carregar dados: " + e.getMessage());
	        }
	    }

	    private Automovel buscarPorPlaca(String placa) {
	        for (Automovel a : lista) {
	            if (a.getPlaca().equalsIgnoreCase(placa)) return a;
	        }
	        return null;
	    }
	}
