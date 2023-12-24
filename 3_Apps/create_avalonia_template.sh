#!/usr/bin/env bash

# To install the Avalonia templates, run this script from the root of the repo.
dotnet new install Avalonia.Templates

# To create a new Avalonia project, run this script from the root of the repo.
dotnet new avalonia.app -o csharp_app
