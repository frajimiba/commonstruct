package com.github.frajimiba.commonstruct.specification.common;

import java.util.regex.Pattern;

import com.github.frajimiba.commonstruct.specification.CompositeSpecification;

/**
 * Valida que un cif introducido sea correcto según la Orden EHA/451/2008.
 *
 * Ver http://www.boe.es/buscar/doc.php?id=BOE-A-2008-3580
 *
 * @author Francisco José Jiménez
 *
 */
public class CifSpecification extends CompositeSpecification<String> {

  /**
   * Patron de formato del CIF.
   */
  private static final Pattern CIF_PATTERN = Pattern.compile("[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]");
  /**
   * Sólo admiten números como carácter de control.
   */
  private static final String CONTROL_SOLO_NUMEROS = "ABDEFGHJUV";
  /**
   * Sólo admiten letras como carácter de control.
   */
  private static final String CONTROL_SOLO_LETRAS = "CKLMNPQRSW";
  /**
   * Conversión de dígito a carácter de control.
   */
  private static final String CONTROL_NUMERO = "JABCDEFGHI";
  /**
   * Longitud de la cadena de un CIF.
   */
  private static final int LONGITUD = 9;

  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(String candidato) {

    boolean result = false;

    if (candidato != null && candidato.length() != 0 && CIF_PATTERN.matcher(candidato).matches()) {
      int parA = getParA(candidato);
      int nonB = getNonB(candidato);

      int parcialC = parA + nonB;
      int digitoE = parcialC % CONTROL_NUMERO.length();
      int digitoD = 0;
      if (digitoE > 0) {
        digitoD = CONTROL_NUMERO.length() - digitoE;
      }
      char letraIni = candidato.charAt(0);
      char caracterFin = candidato.charAt(LONGITUD - 1);

      result = caracterControlValido(letraIni, caracterFin, digitoD);
    }

    return result;
  }

  /**
   * Suma los dígitos de las posiciones pares.
   *
   * @param candicato
   *          El CIF candidato a validar
   *
   * @return La suma de los dígitos pares
   */
  private int getParA(String candicato) {

    int parA = 0;
    for (int i = 2; i < LONGITUD - 1; i += 2) {
      int digit = Character.digit(candicato.charAt(i), CONTROL_NUMERO.length());
      parA += digit;
    }

    return parA;
  }

  /**
   * Para cada uno de los dígitos de la posiciones impares, multiplicarlo por 2
   * y sumar los dígitos del resultado.
   *
   * @param candicato
   *          El CIF candidato a validar
   *
   * @return Para cada uno de los dígitos de la posiciones impares,
   *         multiplicarlo por 2 y sumar los dígitos del resultado.
   */
  private int getNonB(String candicato) {
    int nonB = 0;
    for (int i = 1; i < LONGITUD; i += 2) {
      int digit = Character.digit(candicato.charAt(i), CONTROL_NUMERO.length());
      int nn = 2 * digit;
      if (nn > LONGITUD) {
        nn = 1 + (nn - CONTROL_NUMERO.length());
      }
      nonB += nn;
    }
    return nonB;
  }

  /**
   * Evalua si el carácter de control es válido como letra.
   *
   * @param letraIni
   *          Primer carácter
   * @param caracterFin
   *          Último carácter
   * @param digitoD
   *          Posición del dígito de control
   * @return <code>true</code> si el carácter de control es válido como letra.
   */
  private boolean validoComoLetra(char letraIni, char caracterFin, int digitoD) {
    return CONTROL_SOLO_LETRAS.indexOf(letraIni) != -1 
        && CONTROL_NUMERO.charAt(digitoD) == caracterFin;
  }

  /**
   * Evalua si el carácter de control es válido como dígito.
   *
   * @param letraIni
   *          Primer carácter
   * @param caracterFin
   *          Último carácter
   * @param digitoD
   *          Posición del dígito de control
   * @return <code>true</code> si el carácter de control es válido como dígito.
   */
  private boolean validoComoDigito(char letraIni, char caracterFin, int digitoD) {
    return CONTROL_SOLO_NUMEROS.indexOf(letraIni) != -1
        && digitoD == Character.digit(caracterFin, CONTROL_NUMERO.length());
  }

  /**
   * Evalua si el carácter de control es válido.
   *
   * @param letraIni
   *          Primer carácter
   * @param caracterFin
   *          Último carácter
   * @param digitoD
   *          Posición del dígito de control
   * @return <code>true</code> si el carácter de control es válido.
   */
  private boolean caracterControlValido(char letraIni, char caracterFin, int digitoD) {
    return validoComoLetra(letraIni, caracterFin, digitoD) 
        || validoComoDigito(letraIni, caracterFin, digitoD);
  }
}