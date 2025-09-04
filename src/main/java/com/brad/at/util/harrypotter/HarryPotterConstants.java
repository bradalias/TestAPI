package com.brad.at.util.harrypotter;

public class HarryPotterConstants
{
    public enum Paths
    {
        BOOKS("/books");

        private String path;

        Paths(String inPath)
        {
            this.path = inPath;
        }

        @Override
        public String toString()
        {
            return this.path;
        }
    }
}
