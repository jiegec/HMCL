/*
 * Copyright 2013 huangyuhui <huanghongxun2008@126.com>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.
 */
package org.jackhuang.hellominecraft.launcher.utils.version;

/**
 *
 * @author hyh
 */
public class Natives implements Cloneable {
    public String windows, osx, linux;

    @Override
    protected Object clone() {
        Natives n = new Natives();
        n.windows = windows;
        n.osx = osx;
        n.linux = linux;
        return n;
    }
}
