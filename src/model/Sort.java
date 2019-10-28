package model;

import java.util.ArrayList;

public class Sort {
	
	public static void merge (int[] arrayIzq, int[] arrayDcha, int[] array, int inicio, int fin, int medio) {
		
		//Marcadores
		arrayIzq[medio] = 999999;
		arrayDcha[fin] = 999999;
		
		int i = inicio;
		int j = medio + 1;
		
		for (int k=inicio; k<=fin; k++) { //Rellenar la lista
			if(arrayIzq[i-1] < arrayDcha[j-1]) { 
				array[k-1] = arrayIzq[i-1]; 
				i++;
			}
			else {
				array[k-1] = arrayDcha[j-1];
				j++;
			}
		}
	}
	
	public static void mergesort (int[] array, int inicio, int fin){
		
		if(inicio<fin) {
			int medio = (inicio + fin) / 2;
			int[] arrayDcha =  new int[fin+1]; //Listas auxs
			int[] arrayIzq =  new int[fin+1];
			
			for(int i=inicio; i<=medio; i++) {
				arrayIzq[i-1]  = array[i-1];
			}
			for(int i=medio+1; i<=fin; i++) {
				arrayDcha[i-1]  = array[i-1];
			}			

			mergesort(arrayIzq, inicio, medio);	
			mergesort(arrayDcha, medio+1, fin);
			
			merge(arrayIzq, arrayDcha, array, inicio, fin, medio);

		}
		
		
	}
	
	public static void mergeNombres (String[] arrayIzq, String[] arrayDcha, String[] array, int inicio, int fin, int medio) {
		
		//Marcadores
		arrayIzq[medio] = "zzzzzzzzzzzzz";
		arrayDcha[fin] = "zzzzzzzzzzzzz";
		
		int i = inicio;
		int j = medio + 1;
		
		for (int k=inicio; k<=fin; k++) { //Rellenar la lista
			if(arrayIzq[i-1].compareTo(arrayDcha[j-1]) < 0) { //arrayIzq[i-1] < arrayDcha[j-1]
				array[k-1] = arrayIzq[i-1]; 
				i++;
			}
			else {
				array[k-1] = arrayDcha[j-1];
				j++;
			}
		}
	}
	
	public static void mergesortNombres (String[] array, int inicio, int fin){
		
		if(inicio<fin) {
			int medio = (inicio + fin) / 2;
			String[] arrayDcha =  new String[fin+1]; //Listas auxs
			String[] arrayIzq =  new String[fin+1];
			
			for(int i=inicio; i<=medio; i++) {
				arrayIzq[i-1]  = array[i-1];
			}
			for(int i=medio+1; i<=fin; i++) {
				arrayDcha[i-1]  = array[i-1];
			}			

			mergesortNombres(arrayIzq, inicio, medio);	
			mergesortNombres(arrayDcha, medio+1, fin);
			
			mergeNombres(arrayIzq, arrayDcha, array, inicio, fin, medio);

		}
		
		
	}
	
	public static void main(String[] args) {		
		String[] nombres = {"Diego", "Zan", "Ana Pere", "Isa", "Ana Peze"}; 
		mergesortNombres(nombres, 1, nombres.length);
		
		for(int i =0; i<nombres.length; i++) {
			System.out.print(nombres[i]+ " , ");
		}
		
		System.out.println(" ");
		
		
		int[] numeros = {9, 3, 5, 2, 1, 6, 4, 3};
		mergesort(numeros, 1, numeros.length);
		
		for(int i =0; i<numeros.length; i++) {
			System.out.print(numeros[i]+ " ");
		}
	}
	

}
