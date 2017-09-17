package com.github.frajimiba.commonstruct.specification.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.frajimiba.commonstruct.specification.CompositeSpecification;

/**
 * Valida que un cif introducido sea correcto.
 *
 * @author Francisco José Jiménez
 *
 */
public class NifSpecification extends CompositeSpecification<String> {

  /**
   * Conversión de dígito a carácter de control.
   */
  private static final String CADENACONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";
  /**
   * Patron de formato del NIF.
   */
  private static final Pattern PATRON = Pattern.compile("[0-9]{8,8}[A-Z]");
  /**
   * Longitud de la cadena de un NIF.
   */
  private static final int LONGITUD = 9;
  /**
   * Posible carácter inicial.
   */
  private static final String CARACTERINICIAL = "X";

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSatisfiedBy(String candidate) {

    boolean result = false;

    if (candidate != null) {

      String nif = candidate.trim();

      if (nif.length() != 0) {

        nif = nif.toUpperCase();

        if (nif.startsWith(CARACTERINICIAL)) {
          nif = nif.replaceFirst(CARACTERINICIAL, "0");
        }

        Matcher matcher = PATRON.matcher(nif);

        if (matcher.matches()) {
          String dni = nif.substring(0, LONGITUD - 1);
          String digitoControl = nif.substring(LONGITUD - 1, LONGITUD);

          int posicion = Integer.parseInt(dni) % CADENACONTROL.length();

          String digitoCalculado = CADENACONTROL.substring(posicion, posicion + 1);

          if (digitoControl.equalsIgnoreCase(digitoCalculado)) {
            result = true;
          }
        }
      }
    }
    return result;
  }
}