package io.sansam;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    public static void main(String[] args) {
        String s = "#spring=xxx";
        final int ci = s.indexOf("#");
        System.out.println(ci);
        if (ci >=0 ) {
            String line = s.substring(0, ci);
            System.out.println(line);
        }

    }
}
