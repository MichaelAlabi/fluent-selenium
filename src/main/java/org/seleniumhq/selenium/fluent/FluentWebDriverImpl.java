/*
Copyright 2011 Software Freedom Conservancy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public final class FluentWebDriverImpl extends FluentCore implements FluentWebDriver {

    public FluentWebDriverImpl(WebDriver delegate) {
        super(delegate, "?");
    }

    @Override
    protected <T> T getFluentWebElement(WebElement result, String context, Class<T> webElementClass) {
        return makeFluentWebElement(super.delegate, result, context, webElementClass.getConstructors()[0]);
    }


    @Override
    protected FluentWebElements getFluentWebElements(List<WebElement> results, String context) {
        return new FluentWebElements(super.delegate, results, context);
    }

    protected final WebElement findIt(By by) {
        return delegate.findElement(by);
    }

    @Override
    protected List<WebElement> findThem(By by) {
        return delegate.findElements(by);
    }
}
