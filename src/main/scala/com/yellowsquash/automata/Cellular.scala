package com.yellowsquash.automata

import com.yellowsquash.automata.Boundary.Boundary
import com.yellowsquash.automata.Neighbor._

import scala.util.Random.nextInt

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

class Cellular(val width: Int
  , val height: Int
  , var matrix: List[List[Int]]) {

  def this(width: Int, height: Int, initialization: Int) {
    this(width
      , height
      , Cellular.generate(height, width, initialization)
    )
  }

  def getNeighbor(x: Int, y: Int, n: Neighbor): Int = {
    n match {
      case N => matrix(x)(Math.floorMod(y - 1, height))
      case E => matrix((x + 1) % width)(y)
      case S => matrix(x)((y + 1) % height)
      case W => matrix(Math.floorMod(x - 1, width))(y)
      case NE => matrix((x + 1) % width)(Math.floorMod(y - 1, height))
      case SE => matrix((x + 1) % width)((y + 1) % height)
      case SW => matrix(Math.floorMod(x - 1, width))((y + 1) % height)
      case NW => matrix(Math.floorMod(x - 1, width))(Math.floorMod(y - 1, height))
      case NN => matrix(x)(Math.floorMod(y - 2, height))
      case EE => matrix((x + 2) % width)(y)
      case SS => matrix(x)((y + 2) % height)
      case WW => matrix(Math.floorMod(x - 2, width))(y)
    }
  }

  def getNeighbors(x: Int, y: Int, ns: List[Neighbor]): List[Int] = {
    ns.map(n => this.getNeighbor(x, y, n))
  }

  def getUpdatedElement(x: Int, y: Int): Int = {
    val c = matrix(x)(y)
    val ns = Neighborhood.getNeighbors(Neighborhood.MOORE)
    val s = getNeighbors(x, y, ns).sum 
    if (s >= 5 || (c == 1 && s >= 4)) 1 else 0
  }

  def update(): Unit = {
    this.matrix = Array.range(0, height)
      .map(x => Array.range(0, width).map(y => getUpdatedElement(x, y)).toList)
      .toList
  }

}

object Cellular {

  def generate(height: Int, width: Int, initialization: Int): List[List[Int]] = {
      Array.range(0, height)
        .map(x => Stream.continually(nextInt(10))
                        .map(y => if (y < initialization) 1 else 0)
                        .take(width)
                        .toList)
        .toList
  }

  def print(m: List[List[Int]]) = {
    Array.range(0, m.length).foreach(x => println(m(x).mkString(" ")))
  }

  def toRGBValues(m: List[List[Int]], onColor: String, offColor: String): List[Int] = {
    m.flatMap(x => x.flatMap(v => {if (v == 1) List(255,255,255) else List(0,0,0)}))
  }

  def toBitmap(c: Cellular): Unit = {
    val img = new BufferedImage(c.height, c.width, BufferedImage.TYPE_INT_RGB)
    val flattenedData = Cellular.toRGBValues(c.matrix, "0", "1").toArray
    img.getRaster().setPixels(0, 0, c.width, c.height, flattenedData)
    ImageIO.write(img, "bmp",new File("out.bmp"));
  }

}

class Color(val red: Int, val green: Int, val blue: Int) {
  def HexadecimalToRGB(hex: String): Color = {
    val r = HexadecimalToDecimal(hex.substring(0, 2)).toInt
    val g = HexadecimalToDecimal(hex.substring(2, 2)).toInt
    val b = HexadecimalToDecimal(hex.substring(4, 2)).toInt
    new Color(r, g, b);
  }

  def HexadecimalToDecimal(hex: String): Double = {
    val hexLength = hex.length()
    var dec = 0d

    for (i <- 0 to hexLength) {
      var b = hex.charAt(i).toInt

      if (b >= 48 && b <= 57) 
        b -= 48
      else if (b >= 65 && b <= 70) 
        b -= 55
      
      dec = dec + (b * Math.pow(16, ((hexLength - i) - 1)))
    }
    
    dec
  }

}


