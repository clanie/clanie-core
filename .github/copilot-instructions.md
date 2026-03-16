# Core - Copilot Instructions

## Project Overview
Core classes and utilities shared across Clanie Java projects.

## Tech Stack
- **Backend:** Java 25+, Spring Boot 3.x
- **Build Tool:** Maven 3.x
- **Testing:** JUnit 5, Spring Boot Test

## Coding Standards

### Java
- Use Java 25 syntax and features where appropriate
- Always use imports rather than fully qualified class names in code
- Follow Spring Boot best practices
- Use Lombok annotations to reduce boilerplate (e.g., `@Data`, `@Builder`, `@Slf4j`)
- Prefer constructor injection over field injection
- Use meaningful variable and method names
- Keep methods focused and single-purpose
- Add 2 blank lines between methods
- Every Java file must start with the GPL copyright/license notice:
  ```java
  /*
   * Copyright (C) <year>, Claus Nielsen, clausn999@gmail.com
   *
   * This program is free software; you can redistribute it and/or modify
   * it under the terms of the GNU General Public License as published by
   * the Free Software Foundation; either version 3 of the License, or
   * (at your option) any later version.
   *
   * This program is distributed in the hope that it will be useful,
   * but WITHOUT ANY WARRANTY; without even the implied warranty of
   * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   * GNU General Public License for more details.
   *
   * You should have received a copy of the GNU General Public License along
   * with this program; if not, write to the Free Software Foundation, Inc.,
   * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
   */
  ```

### Testing
- Write unit tests
- Use `@SpringBootTest` for integration tests
- Mock external API calls in tests

## Security Guidelines
- Never commit sensitive credentials
- Use environment variables for secrets
